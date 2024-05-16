package com.clo.accloss.products.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.products.data.source.ProductDataSource
import com.clo.accloss.products.domain.mappers.toDatabase
import com.clo.accloss.products.domain.mappers.toDomain
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.products.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    override val productDataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getRemoteProducts(baseUrl: String, company: String): RequestState<List<Product>> {
        return withContext(Dispatchers.IO) {
            when (val apiOperation = productDataSource.productRemote.getSafeProducts(baseUrl)) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data.product.map { productResponseItem ->
                        productResponseItem.toDomain().copy(
                            url = """$baseUrl/img/${productResponseItem.codigo}.jpg""",
                            empresa = company
                        )
                    }

                    addProducts(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override fun getProducts(
        company: String,
        baseUrl: String,
        forceReload: Boolean
    ): Flow<RequestState<List<Product>>> = flow {
        emit(RequestState.Loading)

        productDataSource.productLocal.getProducts(company)
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("PRODUCT REPOSITORY: getProducts")
            }
            .collect { cachedList ->
                emit(
                    RequestState.Success(
                        data = cachedList.map { productEntity ->
                            productEntity.toDomain()
                        }
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override fun getProduct(
        company: String,
        user: String
    ): Flow<RequestState<Product>> = flow {
        emit(RequestState.Loading)

        productDataSource.productLocal.getProduct(
            code = user,
            company = company
        )
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("PRODUCT REPOSITORY: getProducts")
            }
            .collect { productEntity ->
                emit(RequestState.Success(data = productEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addProducts(products: List<Product>) =
        withContext(Dispatchers.IO) {
            productDataSource.productLocal.addProducts(
                products = products.map { product ->
                    product.toDatabase()
                }
            )
        }
}
