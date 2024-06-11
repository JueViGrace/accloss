package com.clo.accloss.products.domain.repository

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.data.source.ProductDataSource
import com.clo.accloss.products.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface ProductRepository {
    val productDataSource: ProductDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteProducts(
        baseUrl: String,
        lastSync: String,
        company: String
    ): RequestState<List<Product>>

    fun getProducts(
        company: String,
        baseUrl: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Product>>>

    fun getProduct(
        company: String,
        product: String
    ): Flow<RequestState<Product>>

    suspend fun addProducts(products: List<Product>)

    suspend fun deleteProducts(company: String)
}
