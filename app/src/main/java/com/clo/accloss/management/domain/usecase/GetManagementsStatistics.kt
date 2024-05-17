package com.clo.accloss.management.domain.usecase

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.management.domain.repository.ManagementRepository
import com.clo.accloss.management.presentation.model.ManagementsUi
import com.clo.accloss.session.domain.usecase.GetSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetManagementsStatistics(
    private val getSession: GetSession,
    private val managementRepository: ManagementRepository
) {
    operator fun invoke(): Flow<RequestState<List<ManagementsUi>>> = flow {
        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> TODO()
                is RequestState.Success -> {
                    managementRepository.getManagementsStatistics(
                        code = sessionResult.data.user,
                        company = sessionResult.data.empresa
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = result.message
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
