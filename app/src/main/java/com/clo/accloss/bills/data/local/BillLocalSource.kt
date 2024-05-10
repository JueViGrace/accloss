package com.clo.accloss.bills.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Factura as BillsEntity

class BillLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getBills(company: String): Flow<List<BillsEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.facturaQueries
                .getFacturas(
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getBill(
        document: String,
        company: String
    ): Flow<BillsEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.facturaQueries
                .getFactura(
                    documento = document,
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addBills(bills: List<BillsEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.facturaQueries.transaction {
                bills.forEach { bill ->
                    db.facturaQueries.addFactura(bill)
                }
            }
        }
    }.await()
}
