package com.clo.accloss.bills.domain.repository

import com.clo.accloss.bills.data.source.BillDataSource
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.bills.presentation.model.BillDetails
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface BillRepository {
    val billDataSource: BillDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteBills(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Bill>>

    fun getBills(
        company: String,
    ): Flow<RequestState<List<Bill>>>

    fun getBillsBySalesman(
        company: String,
        salesman: String
    ): Flow<RequestState<List<Bill>>>

    suspend fun getBill(
        bill: String,
        company: String
    ): RequestState<Bill>

    fun getBillWithLines(
        bill: String,
        company: String
    ): Flow<RequestState<BillDetails>>

    suspend fun addBills(bills: List<Bill>)
}
