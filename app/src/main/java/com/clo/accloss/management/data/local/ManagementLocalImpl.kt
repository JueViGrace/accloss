package com.clo.accloss.management.data.local

import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import com.clo.accloss.Gerencia as ManagementEntity

class ManagementLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : ManagementLocal {
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
