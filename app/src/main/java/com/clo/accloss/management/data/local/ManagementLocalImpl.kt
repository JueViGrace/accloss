package com.clo.accloss.management.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.GetManagementStatistics
import com.clo.accloss.GetManagementsStatistics
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Gerencia as ManagementEntity

class ManagementLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : ManagementLocal {
    override suspend fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<List<GetManagementsStatistics>> = scope.async {
        dbHelper.withDatabase { db ->
            db.gerenciaQueries
                .getManagementsStatistics(
                    codigo = code,
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }
    }.await()

    override suspend fun getManagementStatistics(
        code: String,
        company: String,
    ): Flow<GetManagementStatistics> = scope.async {
        dbHelper.withDatabase { db ->
            db.gerenciaQueries
                .getManagementStatistics(
                    codigo = code,
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    override suspend fun addManagements(managements: List<ManagementEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.gerenciaQueries.transaction {
                managements.forEach { management ->
                    db.gerenciaQueries.addGerencia(management)
                }
            }
        }
    }.await()
}
