package com.clo.accloss.products.domain.repository

import androidx.paging.map
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.data.local.ProductLocalDataSource
import com.clo.accloss.products.data.remote.source.ProductRemoteDataSource
import com.clo.accloss.products.domain.mappers.toDatabase
import com.clo.accloss.products.domain.mappers.toDomain
import com.clo.accloss.products.domain.model.Product
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
    /*private fun getRemoteProducts(
        baseUrl: String,
        empresa: String
    ): Flow<List<Product>> = flow<List<Product>> {
        productRemoteDataSource
            .getSafeProducts(
                baseUrl = baseUrl
            )
            .collect { apiOperation ->
                when (apiOperation) {
                    is ApiOperation.Failure -> emit(emptyList())
                    is ApiOperation.Loading -> emit(emptyList())
                    is ApiOperation.Success -> emit(
                        apiOperation.data.product.map { productResponseItem ->
                            productResponseItem.toDomain().copy(
                                url = """$baseUrl/img/${productResponseItem.codigo}.jpg""",
                                empresa = empresa
                            )
                        }
                    )
                }
            }
    }.flowOn(Dispatchers.IO)*/

    private fun getRemoteProducts(
        baseUrl: String,
        empresa: String
    ): Flow<RequestState<List<Product>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = productRemoteDataSource
            .getSafeProducts(
                baseUrl = baseUrl
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error.message ?: SERVER_ERROR
                    )
                )
            }
            is ApiOperation.Success -> {
                emit(
                    RequestState.Success(
                        data = apiOperation.data.product.map { productResponseItem ->
                            productResponseItem.toDomain().copy(
                                url = """$baseUrl/img/${productResponseItem.codigo}.jpg""",
                                empresa = empresa
                            )
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getProducts(
        empresa: String,
        baseUrl: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Product>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        productLocalDataSource.getProducts(empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->

                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { productEntity ->
                                productEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteProducts(
                        baseUrl = baseUrl,
                        empresa = empresa
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = result.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                result.data.forEach { product ->
                                    addProduct(product)
                                }
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getProduct(
        empresa: String,
        user: String
    ): Flow<RequestState<Product>> = flow {
        emit(RequestState.Loading)

        productLocalDataSource.getProduct(
            codigo = user,
            empresa = empresa
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { productEntity ->
                emit(RequestState.Success(data = productEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addProduct(product: Product) =
        productLocalDataSource.addProduct(product = product.toDatabase())
}
