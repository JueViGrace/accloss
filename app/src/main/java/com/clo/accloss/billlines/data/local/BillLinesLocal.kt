package com.clo.accloss.billlines.data.local

import kotlinx.coroutines.CoroutineScope
import com.clo.accloss.Lineas_factura as BillLinesEntity

interface BillLinesLocal {
    val scope: CoroutineScope

    suspend fun getBillLines(
        document: String,
        company: String
    ): List<BillLinesEntity>

    suspend fun addBillLines(billLines: List<BillLinesEntity>)

    suspend fun deleteBillLines(company: String)
}
