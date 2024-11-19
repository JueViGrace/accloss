package com.clo.accloss.rate.domain.usecase

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.rate.domain.model.Rate
import com.clo.accloss.rate.domain.repository.RateRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRate(
    private val getSession: GetCurrentUser,
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
                    ratesRepository.getRate(
                        company = sessionResult.data.empresa
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                ratesRepository.deleteRate(
                                    company = sessionResult.data.empresa
                                )
                                val apiResult = ratesRepository.getRemoteRate(
                                    baseUrl = sessionResult.data.enlaceEmpresa,
                                    company = sessionResult.data.empresa
                                )
                                when (apiResult) {
                                    is RequestState.Error -> {
                                        emit(
                                            RequestState.Error(
                                                apiResult.message
                                            )
                                        )
                                    }
                                    is RequestState.Success -> {
                                    }
                                    else -> emit(RequestState.Loading)
                                }
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
