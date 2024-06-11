package com.clo.accloss.management.data.local

import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import com.clo.accloss.Gerencia as ManagementEntity

class DefaultManagementLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope,
) : ManagementLocal {
    override suspend fun addManagements(managements: List<ManagementEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.gerenciaQueries.transaction {
                    managements.forEach { management ->
                        db.gerenciaQueries.addGerencia(management)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteManagements(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.gerenciaQueries.transaction {
                    db.gerenciaQueries.deleteGerencias(empresa = company)
                }
            }
        }.await()
    }
}
