package com.clo.accloss.products.data.local

import com.clo.accloss.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ProductLocal {
    val scope: CoroutineScope

    suspend fun getProducts(company: String): Flow<List<Product>>

    suspend fun getProduct(code: String, company: String): Flow<Product?>

    suspend fun addProducts(products: List<Product>)

    suspend fun deleteProducts(company: String)
}
