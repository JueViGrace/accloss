package com.clo.accloss.bills.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Factura as BillEntity

interface BillLocal {
    suspend fun getBills(company: String): Flow<List<BillEntity>>

    suspend fun getBill(
        document: String,
        company: String
    ): Flow<BillEntity>

    suspend fun addBills(bills: List<BillEntity>)
}
