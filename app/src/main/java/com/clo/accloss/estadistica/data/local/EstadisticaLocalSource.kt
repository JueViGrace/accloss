package com.clo.accloss.estadistica.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Estadistica as EstadisticaEntity

class EstadisticaLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getEstadisiticas(empresa: String): Flow<List<EstadisticaEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries
                .getEstadisticas(
                    empresa = empresa
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }
    }.await()

    suspend fun getEstadisitica(
        empresa: String,
        vendedor: String
    ): Flow<EstadisticaEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries
                .getEstadistica(
                    empresa = empresa,
                    vendedor = vendedor
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    suspend fun addEstadistica(estadistica: EstadisticaEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries.addEstadistica(estadistica)
        }
    }.await()

    suspend fun deleteEstadisitcas(empresa: String) = scope.async {
        dbHelper.withDatabase { db ->
            db.estadisticaQueries.deleteEstadisticas(empresa)
        }
    }.await()
}
