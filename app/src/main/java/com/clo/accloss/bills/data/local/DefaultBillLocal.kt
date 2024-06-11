package com.clo.accloss.bills.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.GetBillWithLines
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Factura as BillEntity

class DefaultBillLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : BillLocal {
    override suspend fun getBills(company: String): Flow<List<BillEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.facturaQueries
                    .getFacturas(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getBillsBySalesman(
        company: String,
        salesman: String
    ): Flow<List<BillEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.facturaQueries
                    .getBillsBySalesman(
                        empresa = company,
                        vendedor = salesman
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getBill(
        document: String,
        company: String
    ): BillEntity? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.facturaQueries
                    .getFactura(
                        documento = document,
                        empresa = company
                    )
                    .executeAsOneOrNull()
            }
        }.await()
    }

    override suspend fun getBillWithLines(
        bill: String,
        company: String,
    ): Flow<List<GetBillWithLines>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.facturaQueries
                    .getBillWithLines(
                        documento = bill,
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun addBills(bills: List<BillEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.facturaQueries.transaction {
                    bills.forEach { bill ->
                        db.facturaQueries.addFactura(bill)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteBills(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.facturaQueries.transaction {
                    db.facturaQueries.deleteFacturas(empresa = company)
                }
            }
        }.await()
    }
}
