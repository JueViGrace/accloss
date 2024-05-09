package com.clo.accloss.gerencia.data.local

import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import com.clo.accloss.Gerencia as GerenciaEntity

class GerenciaLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun addGerencias(gerencias: List<GerenciaEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.gerenciaQueries.transaction {
                gerencias.forEach { gerencia ->
                    db.gerenciaQueries.addGerencia(gerencia)
                }
            }
        }
    }.await()
}
