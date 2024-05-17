package com.clo.accloss.billlines.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Lineas_factura as BillLinesEntity

interface BillLinesLocal {
    suspend fun getBillLines(
        document: String,
        company: String
    ): Flow<List<BillLinesEntity>>

    suspend fun addBillLines(billLines: List<BillLinesEntity>)
}
