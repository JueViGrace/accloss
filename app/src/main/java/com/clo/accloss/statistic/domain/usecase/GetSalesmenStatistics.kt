package com.clo.accloss.statistic.domain.usecase

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSalesmenStatistics(
    private val getCurrentUser: GetCurrentUser,
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(id: String): Flow<RequestState<List<PersonalStatistics>>> = flow {
        emit(RequestState.Loading)

        getCurrentUser().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    if (id.isNotEmpty()) {
                        statisticRepository.getManagementsStatistics(
                            code = id,
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
                                    val personalStatistics = result.data

                                    emit(
                                        RequestState.Success(
                                            data = personalStatistics
                                        )
                                    )
                                }

                                else -> emit(RequestState.Loading)
                            }
                        }
                    } else {
                        statisticRepository.getStatistics(
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
                                    val personalStatistics = result.data.map {
                                        PersonalStatistics(
                                            statistic = it
                                        )
                                    }

                                    emit(
                                        RequestState.Success(
                                            data = personalStatistics
                                        )
                                    )
                                }

                                else -> emit(RequestState.Loading)
                            }
                        }
                    }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}
