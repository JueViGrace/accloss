package com.clo.accloss.products.data.local

import com.clo.accloss.Product
import kotlinx.coroutines.flow.Flow

interface ProductLocal {
    suspend fun getProducts(company: String): Flow<List<Product>>

    suspend fun getProduct(code: String, company: String): Flow<Product>

    suspend fun addProducts(products: List<Product>)
}
