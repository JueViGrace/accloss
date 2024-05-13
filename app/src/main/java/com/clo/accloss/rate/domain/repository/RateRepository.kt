package com.clo.accloss.rate.domain.repository

import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.rate.data.remote.source.RateRemoteSource
import com.clo.accloss.rate.domain.mappers.toDomain
import com.clo.accloss.rate.domain.model.Rate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RateRepository(
    private val ratesRemoteSource: RateRemoteSource
) {
    fun getRemoteRate(
        baseUrl: String
    ): Flow<RequestState<Rate>> = flow {
        emit(RequestState.Loading)

        val apiOperation = ratesRemoteSource
            .getSafeRates(baseUrl = baseUrl)

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error
                    )
                )
            }
            is ApiOperation.Success -> {
                emit(
                    RequestState.Success(
                        data = apiOperation.data.toDomain()
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)
}
