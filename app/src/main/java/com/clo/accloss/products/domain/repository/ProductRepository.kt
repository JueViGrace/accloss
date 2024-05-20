package com.clo.accloss.products.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.products.data.source.ProductDataSource
import com.clo.accloss.products.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val productDataSource: ProductDataSource

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
        user: String
    ): Flow<RequestState<Product>>

    suspend fun addProducts(products: List<Product>)
}
