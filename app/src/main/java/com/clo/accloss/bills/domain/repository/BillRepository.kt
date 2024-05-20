package com.clo.accloss.bills.domain.repository

import com.clo.accloss.bills.data.source.BillDataSource
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.core.domain.state.RequestState
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    val billDataSource: BillDataSource

    suspend fun getRemoteBills(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Bill>>

    fun getBills(
        company: String,
    ): Flow<RequestState<List<Bill>>>

    suspend fun addBills(bills: List<Bill>)
}
