package com.clo.accloss.statistic.data.repository

import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.modules.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.statistic.data.source.StatisticDataSource
import com.clo.accloss.statistic.domain.mappers.toDatabase
import com.clo.accloss.statistic.domain.mappers.toDomain
import com.clo.accloss.statistic.domain.mappers.toUi
import com.clo.accloss.statistic.domain.model.Statistic
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultStatisticRepository(
    override val statisticDataSource: StatisticDataSource,
    override val coroutineContext: CoroutineContext
) : StatisticRepository {
    override suspend fun getRemoteStatistics(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Statistic>> {
        return withContext(coroutineContext) {
            when (
                val apiOperation = statisticDataSource.statisticRemote
                    .getSafeStatistic(
                        baseUrl = baseUrl,
                        user = user
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data.map { statisticResponse ->
                        statisticResponse.toDomain().copy(empresa = company)
                    }

                    addStatistic(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override fun getStatistics(
        company: String,
    ): Flow<RequestState<List<Statistic>>> = flow {
        emit(RequestState.Loading)

        statisticDataSource.statisticLocal
            .getStatistics(company = company)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("STATISTIC REPOSITORY: getStatistics")
            }
            .collect { cachedList ->
                if (cachedList.isEmpty()) {
                    return@collect emit(
                        RequestState.Error(
                            message = R.string.empty_list
                        )
                    )
                }
                emit(
                    RequestState.Success(
                        data = cachedList.map { statisticEntity ->
                            statisticEntity.toDomain()
                        }
                    )
                )
            }
    }.flowOn(coroutineContext)

    override suspend fun getStatistic(
        code: String,
        company: String
    ): RequestState<Statistic> {
        val statistic = withContext(coroutineContext) {
            statisticDataSource.statisticLocal.getStatistic(
                seller = code,
                company = company
            )
        }

        return if (statistic != null) {
            RequestState.Success(
                data = statistic.toDomain()
            )
        } else {
            RequestState.Error(
                message = R.string.no_statistics_found
            )
        }
    }

    override fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<RequestState<List<PersonalStatistics>>> = flow {
        emit(RequestState.Loading)

        statisticDataSource.statisticLocal
            .getManagementsStatistics(
                code = code,
                company = company
            )
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("MANAGEMENT REPOSITORY: getManagementsStatistics")
            }
            .collect { list ->
                if (list.isEmpty()) {
                    return@collect emit(
                        RequestState.Error(
                            message = R.string.empty_list
                        )
                    )
                }

                emit(
                    RequestState.Success(
                        data = list.map { getManagementsStatistics ->
                            getManagementsStatistics.toUi()
                        }
                    )
                )
            }
    }.flowOn(coroutineContext)

    override suspend fun getPersonalStatistic(
        code: String,
        company: String,
    ): RequestState<PersonalStatistics> {
        val personalStatistic = withContext(coroutineContext) {
            statisticDataSource.statisticLocal.getSalesmanPersonalStatistic(
                salesman = code,
                company = company
            )
        }

        return if (personalStatistic != null) {
            RequestState.Success(
                data = personalStatistic.toUi()
            )
        } else {
            RequestState.Error(
                message = R.string.no_statistics_found
            )
        }
    }

    override fun getManagementStatistics(
        code: String,
        company: String,
    ): Flow<RequestState<PersonalStatistics>> = flow {
        emit(RequestState.Loading)

        statisticDataSource.statisticLocal
            .getManagementStatistics(
                code = code,
                company = company
            )
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("MANAGEMENT REPOSITORY: getManagementsStatistics")
            }
            .collect { getManagementStatistics ->
                if (getManagementStatistics != null) {
                    emit(
                        RequestState.Success(
                            data = getManagementStatistics.toUi()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.no_statistics_found
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override fun getProfileStatistics(
        company: String
    ): Flow<RequestState<ProfileStatisticsModel>> = flow {
        emit(RequestState.Loading)

        statisticDataSource.statisticLocal
            .getProfileStatistics(company = company)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("STATISTIC REPOSITORY: getProfileStatistics")
            }
            .collect { profileStatisticsEntity ->
                if (profileStatisticsEntity != null) {
                    emit(
                        RequestState.Success(
                            data = profileStatisticsEntity.toUi()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.no_statistics_found
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun addStatistic(statistics: List<Statistic>) {
        withContext(coroutineContext) {
            statisticDataSource.statisticLocal.addStatistics(
                statistics = statistics.map { statistic ->
                    statistic.toDatabase()
                }
            )
        }
    }

    override suspend fun deleteStatistics(company: String) {
        withContext(coroutineContext) {
            statisticDataSource.statisticLocal.deleteStatistics(company = company)
        }
    }
}
