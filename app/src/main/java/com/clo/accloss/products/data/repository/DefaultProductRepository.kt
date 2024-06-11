package com.clo.accloss.products.data.repository

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.data.source.ProductDataSource
import com.clo.accloss.products.domain.mappers.toDatabase
import com.clo.accloss.products.domain.mappers.toDomain
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultProductRepository(
    override val productDataSource: ProductDataSource,
    override val coroutineContext: CoroutineContext
) : ProductRepository {
    override suspend fun getRemoteProducts(
        baseUrl: String,
        lastSync: String,
        company: String
    ): RequestState<List<Product>> {
        return withContext(coroutineContext) {
            when (
                val apiOperation = productDataSource
                    .productRemote
                    .getSafeProducts(
                        baseUrl = baseUrl,
                        lastSync = lastSync
                    )
            ) {
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
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE))
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
    }.flowOn(coroutineContext)

    override fun getProduct(
        company: String,
        product: String
    ): Flow<RequestState<Product>> = flow {
        emit(RequestState.Loading)

        productDataSource.productLocal.getProduct(
            code = product,
            company = company
        )
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("PRODUCT REPOSITORY: getProducts")
            }
            .collect { productEntity ->
                if (productEntity != null) {
                    emit(
                        RequestState.Success(
                            data = productEntity.toDomain()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.this_product_doesn_t_exists
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun addProducts(products: List<Product>) {
        withContext(coroutineContext) {
            productDataSource.productLocal.addProducts(
                products = products.map { product ->
                    product
                        .toDatabase()
                        .copy(
                            nombre = product.nombre
                                .lowercase()
                                .capitalize(Locale.current)
                        )
                }
            )
        }
    }

    override suspend fun deleteProducts(company: String) {
        withContext(coroutineContext) {
            productDataSource.productLocal.deleteProducts(company = company)
        }
    }
}
