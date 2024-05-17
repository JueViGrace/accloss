package com.clo.accloss.statistic.data.local

import com.clo.accloss.GetProfileStatistics
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Estadistica as StatisticsEntity

interface StatisticLocal {
    suspend fun getStatistics(company: String): Flow<List<StatisticsEntity>>

    suspend fun getStatistic(
        company: String,
        seller: String
    ): Flow<StatisticsEntity>

    suspend fun getProfileStatistics(
        company: String
    ): Flow<GetProfileStatistics>

    suspend fun addStatistics(statistics: List<StatisticsEntity>)
}
