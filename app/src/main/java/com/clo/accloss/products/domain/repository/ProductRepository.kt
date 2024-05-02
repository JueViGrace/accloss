package com.clo.accloss.products.domain.repository

import androidx.paging.map
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.data.local.ProductLocalDataSource
import com.clo.accloss.products.data.remote.source.ProductRemoteDataSource
import com.clo.accloss.products.domain.mappers.toDatabase
import com.clo.accloss.products.domain.mappers.toDomain
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.session.domain.model.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) {
    fun getRemoteProducts(
        baseUrl: String
    ): Flow<List<Product>> = productRemoteDataSource
        .getSafeProducts(
            baseUrl = baseUrl
        )
        .map { apiOperation ->
            when (apiOperation) {
                is ApiOperation.Failure -> emptyList()
                is ApiOperation.Loading -> emptyList()
                is ApiOperation.Success -> apiOperation.data.product?.map { productResponseItem ->
                    productResponseItem.toDomain()
                } ?: emptyList()
            }
        }

    fun getProducts(
        session: Session,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Product>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        productLocalDataSource.getProducts(session.empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { value ->

                if (value.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = value.map { productEntity ->
                                productEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteProducts(session.enlaceEmpresa).collect { list ->
                        if (list.isNotEmpty()) {
                            list.forEach { product ->
                                productLocalDataSource.addProduct(
                                    product.copy(
                                        url = """${session.enlaceEmpresa}/img/${product.codigo}.jpg""",
                                        empresa = session.empresa
                                    ).toDatabase()
                                )
                            }
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getProduct(
        codigo: String,
        empresa: String
    ): Flow<RequestState<Product>> = flow {
        emit(RequestState.Loading)

        productLocalDataSource.getProduct(codigo = codigo, empresa = empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { productEntity ->
                emit(RequestState.Success(data = productEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun addProduct(product: Product) =
        productLocalDataSource.addProduct(product = product.toDatabase())
}
