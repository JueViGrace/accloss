package com.clo.accloss.billlines.data.local

import com.clo.accloss.Lineas_factura as BillLinesEntity

interface BillLinesLocal {
    suspend fun getBillLines(
        document: String,
        company: String
    ): List<BillLinesEntity>

    suspend fun addBillLines(billLines: List<BillLinesEntity>)
}
