package com.clo.accloss.billlines.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Lineas_factura as BillLinesEntity

class BillLinesLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getBillLines(
        document: String,
        company: String
    ): Flow<List<BillLinesEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasFacturaQueries.getLineasFactura(
                documento = document,
                empresa = company
            )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addBillLines(billLines: List<BillLinesEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasFacturaQueries.transaction {
                billLines.forEach { line ->
                    db.lineasFacturaQueries.addLineasFactura(line)
                }
            }
        }
    }.await()
}
