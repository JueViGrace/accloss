package com.clo.accloss.vendedor.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Vendedor as VendedorEntity

class VendedorLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getVendedores(empresa: String): Flow<List<VendedorEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries
                .getVendedores(
                    empresa = empresa
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getVendedor(
        vendedor: String,
        empresa: String
    ): Flow<VendedorEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries
                .getVendedor(
                    vendedor = vendedor,
                    empresa = empresa
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addVendedor(vendedores: List<VendedorEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries.transaction {
                vendedores.forEach { vendedor ->
                    db.vendedorQueries.addVendedor(vendedor = vendedor)
                }
            }
        }
    }.await()
}
