package com.clo.accloss.rate.domain.repository

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.rate.data.source.RateDataSource
import com.clo.accloss.rate.domain.model.Rate
import kotlinx.coroutines.flow.Flow

interface RateRepository {
    val rateDataSource: RateDataSource

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
