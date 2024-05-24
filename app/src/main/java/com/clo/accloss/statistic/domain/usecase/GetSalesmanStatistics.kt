package com.clo.accloss.statistic.domain.usecase

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import com.clo.accloss.statistic.domain.model.Statistic
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSalesmanStatistics(
    private val getCurrentUser: GetCurrentUser,
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(): Flow<RequestState<Statistic>> = flow {
        emit(RequestState.Loading)

        getCurrentUser().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        ))
                }
                is RequestState.Success -> {

                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}