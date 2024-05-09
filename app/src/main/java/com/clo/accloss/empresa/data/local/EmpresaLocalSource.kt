package com.clo.accloss.empresa.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.Empresa
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class EmpresaLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getEmpresas(): Flow<List<Empresa>> = scope.async {
        dbHelper.withDatabase { db ->
            db.empresaQueries
                .getEmpresas()
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getEmpresa(codigo: String): Flow<Empresa> = scope.async {
        dbHelper.withDatabase { db ->
            db.empresaQueries
                .getEmpresa(codigoEmpresa = codigo)
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addEmpresa(empresa: Empresa) = scope.async {
        dbHelper.withDatabase { db ->
            db.empresaQueries.addEmpresa(empresa)
        }
    }.await()
}
