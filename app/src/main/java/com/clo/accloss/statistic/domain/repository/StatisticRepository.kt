package com.clo.accloss.statistic.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.core.modules.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.statistic.data.source.StatisticDataSource
import com.clo.accloss.statistic.domain.model.Statistic
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
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

    suspend fun getStatistic(
        code: String,
        company: String
    ): RequestState<Statistic>

    fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<RequestState<List<PersonalStatistics>>>

    suspend fun getPersonalStatistic(
        code: String,
        company: String
    ): RequestState<PersonalStatistics>

    fun getManagementStatistics(
        code: String,
        company: String
    ): Flow<RequestState<PersonalStatistics>>

    fun getProfileStatistics(
        company: String
    ): Flow<RequestState<ProfileStatisticsModel>>

    suspend fun addStatistic(statistics: List<Statistic>)
}
