package com.clo.accloss.products.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Product as ProductEntity

class DefaultProductLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : ProductLocal {
    override suspend fun getProducts(company: String): Flow<List<ProductEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.productQueries
                    .getProducts(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getProduct(code: String, company: String): Flow<ProductEntity?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.productQueries
                    .getProduct(
                        codigo = code,
                        empresa = company
                    )
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun addProducts(products: List<ProductEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.productQueries.transaction {
                    products.forEach { product ->
                        db.productQueries.addProducts(product)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteProducts(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.productQueries.transaction {
                    db.productQueries.deleteProducts(empresa = company)
                }
            }
        }.await()
    }
}
