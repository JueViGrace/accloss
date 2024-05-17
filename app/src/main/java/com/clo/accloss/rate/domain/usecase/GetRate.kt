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
    operator fun invoke(
        forceReload: Boolean = false
    ): Flow<RequestState<Rate>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

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
                                emit(
                                    RequestState.Error(
                                        message = result.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                if (result.data.empresa.isNotEmpty() && !reload) {
                                    emit(
                                        RequestState.Success(
                                            data = result.data
                                        )
                                    )
                                } else {
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
                                            reload = false
                                        }
                                        else -> emit(RequestState.Loading)
                                    }
                                }
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
