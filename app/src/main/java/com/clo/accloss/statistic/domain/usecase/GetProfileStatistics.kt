package com.clo.accloss.statistic.domain.usecase

import com.clo.accloss.core.presentation.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.usecase.GetSession
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProfileStatistics(
    private val getSession: GetSession,
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(): Flow<RequestState<ProfileStatisticsModel>> = flow {
        emit(RequestState.Loading)

        getSession().collect { sessionResult ->
            when(sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    statisticRepository.getProfileStatistics(
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