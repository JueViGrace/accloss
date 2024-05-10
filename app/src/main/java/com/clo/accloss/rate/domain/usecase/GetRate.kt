package com.clo.accloss.rate.domain.usecase

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.rate.domain.model.Rate
import com.clo.accloss.rate.domain.repository.RateRepository
import com.clo.accloss.session.domain.usecase.GetSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRate(
    private val getSession: GetSession,
    private val ratesRepository: RateRepository
) {
    operator fun invoke(): Flow<RequestState<Rate>> = flow {
        emit(RequestState.Loading)

        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    ratesRepository.getRemoteRate(
                        baseUrl = sessionResult.data.enlaceEmpresa
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        result.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                emit(
                                    RequestState.Success(
                                        data = result.data
                                    )
                                )
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}
