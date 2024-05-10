package com.clo.accloss.statistic.domain.repository

import android.util.Log
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.statistic.data.local.StatisticsLocalSource
import com.clo.accloss.statistic.data.remote.source.StatisticRemoteSource
import com.clo.accloss.statistic.domain.mappers.toDatabase
import com.clo.accloss.statistic.domain.mappers.toDomain
import com.clo.accloss.statistic.domain.mappers.toUiModel
import com.clo.accloss.statistic.domain.model.Statistic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StatisticRepository(
    private val statisticRemoteSource: StatisticRemoteSource,
    private val statisticLocalSource: StatisticsLocalSource
) {
    fun getRemoteStatistics(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Statistic>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = statisticRemoteSource
            .getSafeStatistic(
                baseUrl = baseUrl,
                user = user
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error.message ?: SERVER_ERROR
                    )
                )
            }
            is ApiOperation.Success -> {
                val data = apiOperation.data.map { statisticResponse ->
                    statisticResponse.toDomain().copy(empresa = company)
                }

                addStatistic(data)

                emit(
                    RequestState.Success(
                        data = data
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getStatistics(
        baseUrl: String,
        user: String,
        company: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Statistic>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        statisticLocalSource.getStatistics(company)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { statisticEntity ->
                                statisticEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteStatistics(
                        baseUrl = baseUrl,
                        user = user,
                        company = company
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
                                Log.i("Force reload Statistics", "getStatistics: ${result.data}")
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    fun getProfileStatistics(
        company: String
    ): Flow<RequestState<ProfileStatisticsModel>> = flow {
        emit(RequestState.Loading)

        statisticLocalSource.getProfileStatistics(company = company)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { profileStatisticsEntity ->
                emit(
                    RequestState.Success(
                        data = profileStatisticsEntity.toUiModel()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addStatistic(statistics: List<Statistic>) =
        statisticLocalSource.addStatistics(
            statistics = statistics.map { statistic ->
                statistic.toDatabase()
            }
        )
}
