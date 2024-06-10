package com.clo.accloss.bills.data.local

import com.clo.accloss.GetBillWithLines
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Factura as BillEntity

interface BillLocal {
    suspend fun getBills(company: String): Flow<List<BillEntity>>

    suspend fun getBillsBySalesman(
        company: String,
        salesman: String
    ): Flow<List<BillEntity>>

    suspend fun getBill(
        document: String,
        company: String
    ): BillEntity?

    suspend fun getBillWithLines(
        bill: String,
        company: String
    ): Flow<List<GetBillWithLines>>

    suspend fun addBills(bills: List<BillEntity>)
}
