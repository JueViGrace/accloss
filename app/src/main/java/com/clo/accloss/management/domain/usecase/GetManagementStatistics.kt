package com.clo.accloss.management.domain.usecase

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetManagementStatistics(
    private val getSession: GetCurrentUser,
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(
        code: String
    ): Flow<RequestState<PersonalStatistics>> = flow {
        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    statisticRepository.getManagementStatistics(
                        code = code,
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
