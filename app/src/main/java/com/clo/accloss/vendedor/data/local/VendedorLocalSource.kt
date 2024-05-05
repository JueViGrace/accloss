package com.clo.accloss.vendedor.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.Vendedor
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class VendedorLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getVendedores(empresa: String): Flow<List<Vendedor>> = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries
                .getVendedores(
                    empresa = empresa
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }
    }.await()

    suspend fun getVendedor(
        vendedor: String,
        empresa: String
    ): Flow<Vendedor> = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries
                .getVendedor(
                    vendedor = vendedor,
                    empresa = empresa
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    suspend fun addVendedor(vendedor: Vendedor) = scope.async {
        dbHelper.withDatabase { db ->
            db.vendedorQueries.addVendedor(vendedor = vendedor)
        }
    }.await()
}
