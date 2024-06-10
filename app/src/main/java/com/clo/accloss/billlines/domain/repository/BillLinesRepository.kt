package com.clo.accloss.billlines.domain.repository

import com.clo.accloss.billlines.data.source.BillLinesDataSource
import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.core.state.RequestState
import kotlin.coroutines.CoroutineContext

interface BillLinesRepository {
    val billLinesDataSource: BillLinesDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteBillLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<BillLines>>

    suspend fun getBillLines(
        document: String,
        company: String,
    ): List<BillLines>

    suspend fun addBillLines(billLines: List<BillLines>)
}
