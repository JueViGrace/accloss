package com.clo.accloss.billlines.data.local

import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import com.clo.accloss.Lineas_factura as BillLinesEntity

class DefaultBillLinesLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope,
) : BillLinesLocal {
    override suspend fun getBillLines(
        document: String,
        company: String
    ): List<BillLinesEntity> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.lineasFacturaQueries
                    .getLineasFactura(
                        documento = document,
                        empresa = company
                    )
                    .executeAsList()
            }
        }.await()
    }

    override suspend fun addBillLines(billLines: List<BillLinesEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.lineasFacturaQueries.transaction {
                    billLines.forEach { line ->
                        db.lineasFacturaQueries.addLineasFactura(line)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteBillLines(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.lineasFacturaQueries.transaction {
                    db.lineasFacturaQueries.deleteLineasFactura(empresa = company)
                }
            }
        }.await()
    }
}
