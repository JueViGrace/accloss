package com.clo.accloss.statistic.data.local

import com.clo.accloss.GetManagementStatistics
import com.clo.accloss.GetManagementsStatistics
import com.clo.accloss.GetProfileStatistics
import com.clo.accloss.GetSalesmanPersonalStatistic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Estadistica as StatisticsEntity

interface StatisticLocal {
    val scope: CoroutineScope

    suspend fun getStatistics(company: String): Flow<List<StatisticsEntity>>

    suspend fun getStatistic(
        company: String,
        seller: String
    ): StatisticsEntity?

    suspend fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<List<GetManagementsStatistics>>

    suspend fun getProfileStatistics(
        company: String
    ): Flow<GetProfileStatistics?>

    suspend fun getManagementStatistics(
        code: String,
        company: String
    ): Flow<GetManagementStatistics?>

    suspend fun getSalesmanPersonalStatistic(
        salesman: String,
        company: String
    ): GetSalesmanPersonalStatistic?

    suspend fun addStatistics(statistics: List<StatisticsEntity>)

    suspend fun deleteStatistics(company: String)
}
