package com.clo.accloss.statistic.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.clo.accloss.GetManagementStatistics
import com.clo.accloss.GetManagementsStatistics
import com.clo.accloss.GetProfileStatistics
import com.clo.accloss.GetSalesmanPersonalStatistic
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Estadistica as StatisticsEntity

class DefaultStatisticsLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : StatisticLocal {
    override suspend fun getStatistics(company: String): Flow<List<StatisticsEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries
                    .getEstadisticas(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getStatistic(
        company: String,
        seller: String
    ): StatisticsEntity? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries
                    .getEstadistica(
                        empresa = company,
                        vendedor = seller
                    )
                    .executeAsOneOrNull()
            }
        }.await()
    }

    override suspend fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<List<GetManagementsStatistics>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries
                    .getManagementsStatistics(
                        codigo = code,
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getProfileStatistics(
        company: String
    ): Flow<GetProfileStatistics?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries
                    .getProfileStatistics(
                        empresa = company
                    )
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getManagementStatistics(
        code: String,
        company: String,
    ): Flow<GetManagementStatistics?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries
                    .getManagementStatistics(
                        codigo = code,
                        empresa = company
                    )
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getSalesmanPersonalStatistic(
        salesman: String,
        company: String,
    ): GetSalesmanPersonalStatistic? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries
                    .getSalesmanPersonalStatistic(
                        vendedor = salesman,
                        empresa = company
                    )
                    .executeAsOneOrNull()
            }
        }.await()
    }

    override suspend fun addStatistics(statistics: List<StatisticsEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries.transaction {
                    statistics.forEach { statistic ->
                        db.estadisticaQueries.addEstadistica(statistic)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteStatistics(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.estadisticaQueries.transaction {
                    db.estadisticaQueries.deleteEstadisticas(empresa = company)
                }
            }
        }.await()
    }
}
