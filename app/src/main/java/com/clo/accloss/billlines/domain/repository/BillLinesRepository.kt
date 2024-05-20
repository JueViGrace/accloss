package com.clo.accloss.billlines.domain.repository

import com.clo.accloss.billlines.data.source.BillLinesDataSource
import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.core.domain.state.RequestState
import kotlinx.coroutines.flow.Flow

interface BillLinesRepository {
    val billLinesDataSource: BillLinesDataSource

    suspend fun getRemoteBillLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<BillLines>>

    fun getBillLines(
        document: String,
        company: String,
    ): Flow<RequestState<List<BillLines>>>

    suspend fun addBillLines(billLines: List<BillLines>)
}
