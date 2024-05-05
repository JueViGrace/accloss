package com.clo.accloss.gerencia.data.local

import com.clo.accloss.Gerencia
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class GerenciaLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun addGerencias(gerencia: Gerencia) = scope.async {
        dbHelper.withDatabase { db ->
            db.gerenciaQueries.addGerencia(gerencia)
        }
    }.await()
}
