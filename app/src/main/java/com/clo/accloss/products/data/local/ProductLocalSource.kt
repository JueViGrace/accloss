package com.clo.accloss.products.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
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
        }
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
        }
    }.await()

    suspend fun addProduct(product: ProductEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries.addProducts(product)
        }
    }.await()
}
