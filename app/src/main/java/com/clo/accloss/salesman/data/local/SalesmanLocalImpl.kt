package com.clo.accloss.salesman.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Vendedor as SalesmenEntity

class SalesmanLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : SalesmanLocal {
    override suspend fun getSalesmen(company: String): Flow<List<SalesmenEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries
                .getVendedores(
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getSalesman(
        salesman: String,
        company: String
    ): Flow<SalesmenEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries
                .getVendedor(
                    vendedor = salesman,
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addSalesmen(salesmen: List<SalesmenEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries.transaction {
                salesmen.forEach { salesman ->
                    db.vendedorQueries.addVendedor(vendedor = salesman)
                }
            }
        }
    }.await()
}
