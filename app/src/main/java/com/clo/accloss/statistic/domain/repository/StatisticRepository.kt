package com.clo.accloss.statistic.domain.repository

import com.clo.accloss.core.presentation.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.statistic.data.source.StatisticDataSource
import com.clo.accloss.statistic.domain.model.Statistic
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    val statisticDataSource: StatisticDataSource

    suspend fun getRemoteStatistics(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Statistic>>

    fun getStatistics(
        company: String,
    ): Flow<RequestState<List<Statistic>>>

    fun getProfileStatistics(
        company: String
    ): Flow<RequestState<ProfileStatisticsModel>>

    suspend fun addStatistic(statistics: List<Statistic>)
}
