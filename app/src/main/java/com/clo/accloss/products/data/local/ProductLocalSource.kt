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

class ProductLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    /*fun getProducts(empresa: String): Flow<Flow<PagingData<ProductEntity>>> = flow {
        emit(
            scope.async {
                dbHelper.withDatabase { db ->
                    Pager(
                        PagingConfig(
                            pageSize = MIN_PAGE,
                            prefetchDistance = PREFETCH
                        )
                    ) {
                        QueryPagingSource(
                            countQuery = db.productQueries.countProducts(empresa),
                            transacter = db.productQueries,
                            context = scope.coroutineContext,
                            queryProvider = { limit, offset ->
                                db.productQueries.getProducts(
                                    empresa = empresa,
                                    limit = limit,
                                    offset = offset
                                )
                            }
                        )
                    }
                        .flow
                }
            }.await()
        )
    }*/

    suspend fun getProducts(empresa: String): Flow<List<ProductEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries
                .getProducts(
                    empresa = empresa
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getProduct(codigo: String, empresa: String): Flow<ProductEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries
                .getProduct(
                    codigo = codigo,
                    empresa = empresa
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addProduct(products: List<ProductEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries.transaction {
                products.forEach { product ->
                    db.productQueries.addProducts(product)
                }
            }
        }
    }.await()
}
