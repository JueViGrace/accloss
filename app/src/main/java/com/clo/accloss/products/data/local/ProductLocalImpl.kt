package com.clo.accloss.products.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Product as ProductEntity

class ProductLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : ProductLocal {
    override suspend fun getProducts(company: String): Flow<List<ProductEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries
                .getProducts(
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getProduct(code: String, company: String): Flow<ProductEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries
                .getProduct(
                    codigo = code,
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addProducts(products: List<ProductEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries.transaction {
                products.forEach { product ->
                    db.productQueries.addProducts(product)
                }
            }
        }
    }.await()
}
