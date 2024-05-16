package com.clo.accloss.salesman.domain.usecase

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.session.domain.usecase.GetSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSellers(
    private val getSession: GetSession,
    private val salesmanRepository: SalesmanRepository
) {
    operator fun invoke(
        forceReload: Boolean = false
    ): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(RequestState.Error(sessionResult.message))
                }
                is RequestState.Success -> {
                    salesmanRepository.getSalesmen(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(RequestState.Error(result.message))
                            }
                            is RequestState.Success -> {
                                if (result.data.isNotEmpty() && !reload) {
                                    emit(RequestState.Success(data = result.data))
                                } else {
                                    val apiResult = salesmanRepository.getRemoteSalesman(
                                        baseUrl = sessionResult.data.enlaceEmpresa,
                                        user = sessionResult.data.user,
                                        company = sessionResult.data.empresa,
                                    )

                                    when (apiResult) {
                                        is RequestState.Error -> {
                                            emit(
                                                RequestState.Error(
                                                    message = apiResult.message
                                                )
                                            )
                                        }
                                        is RequestState.Success -> {
                                            reload = false
                                        }
                                        else -> emit(RequestState.Loading)
                                    }

                                    // TODO: DOWNLOAD CONFIG
                                    val mastersResult = salesmanRepository.getRemoteMasters(
                                        baseUrl = sessionResult.data.enlaceEmpresa,
                                        user = sessionResult.data.user,
                                        company = sessionResult.data.empresa,
                                    )

                                    when (mastersResult) {
                                        is RequestState.Error -> {
                                            emit(
                                                RequestState.Error(
                                                    message = mastersResult.message
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
