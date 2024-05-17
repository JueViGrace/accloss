package com.clo.accloss.statistic.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.GetProfileStatistics
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Estadistica as StatisticsEntity

class StatisticsLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : StatisticLocal {
    override suspend fun getStatistics(company: String): Flow<List<StatisticsEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries
                .getEstadisticas(
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getStatistic(
        company: String,
        seller: String
    ): Flow<StatisticsEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries
                .getEstadistica(
                    empresa = company,
                    vendedor = seller
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getProfileStatistics(
        company: String
    ): Flow<GetProfileStatistics> = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries
                .getProfileStatistics(
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addStatistics(statistics: List<StatisticsEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries.transaction {
                statistics.forEach { statistic ->
                    db.estadisticaQueries.addEstadistica(statistic)
                }
            }
        }
    }.await()
}
