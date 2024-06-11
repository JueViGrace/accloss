package com.clo.accloss.rate.domain.repository

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.rate.data.source.RateDataSource
import com.clo.accloss.rate.domain.model.Rate
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface RateRepository {
    val rateDataSource: RateDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteRate(
        baseUrl: String,
        company: String
    ): RequestState<Rate>

    suspend fun getRate(
        company: String
    ): Flow<RequestState<Rate>>

    suspend fun addRate(rate: Rate)

    suspend fun deleteRate(company: String)
}
