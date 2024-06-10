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

class BillLinesLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : BillLinesLocal {
    override suspend fun getBillLines(
        document: String,
        company: String
    ): List<BillLinesEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasFacturaQueries
                .getLineasFactura(
                documento = document,
                empresa = company
                )
                .executeAsList()
        }
    }.await()

    override suspend fun addBillLines(billLines: List<BillLinesEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasFacturaQueries.transaction {
                billLines.forEach { line ->
                    db.lineasFacturaQueries.addLineasFactura(line)
                }
            }
        }
    }.await()
}
