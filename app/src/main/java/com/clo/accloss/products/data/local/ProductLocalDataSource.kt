package com.clo.accloss.products.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.paging3.QueryPagingSource
import com.clo.accloss.core.common.Constants.MIN_PAGE
import com.clo.accloss.core.common.Constants.PREFETCH
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.clo.accloss.Product as ProductEntity

class ProductLocalDataSource(
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
