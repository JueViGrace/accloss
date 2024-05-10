package com.clo.accloss.company.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Empresa as CompanyEntity

class CompanyLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getCompanies(): Flow<List<CompanyEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.empresaQueries
                .getEmpresas()
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getEmpresa(code: String): Flow<CompanyEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.empresaQueries
                .getEmpresa(codigoEmpresa = code)
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addCompany(company: CompanyEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.empresaQueries.addEmpresa(company)
        }
    }.await()
}
