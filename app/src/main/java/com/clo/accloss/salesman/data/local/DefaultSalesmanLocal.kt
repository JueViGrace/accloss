package com.clo.accloss.salesman.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Vendedor as SalesmenEntity

class DefaultSalesmanLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : SalesmanLocal {
    override suspend fun getSalesmen(company: String): Flow<List<SalesmenEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.vendedorQueries
                    .getVendedores(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getSalesman(
        salesman: String,
        company: String
    ): Flow<SalesmenEntity?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.vendedorQueries
                    .getVendedor(
                        vendedor = salesman,
                        empresa = company
                    )
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun addSalesmen(salesmen: List<SalesmenEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.vendedorQueries.transaction {
                    salesmen.forEach { salesman ->
                        db.vendedorQueries.addVendedor(vendedor = salesman)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteSalesmen(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.vendedorQueries.transaction {
                    db.vendedorQueries.deleteVendedor(empresa = company)
                }
            }
        }.await()
    }
}
