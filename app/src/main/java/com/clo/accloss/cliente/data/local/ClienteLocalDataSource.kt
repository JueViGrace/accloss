package com.clo.accloss.cliente.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.Cliente
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class ClienteLocalDataSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getClientes(empresa: String): Flow<List<Cliente>> = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries
                .getClientes(empresa = empresa)
                .asFlow()
                .mapToList(scope.coroutineContext)
        }
    }.await()

    suspend fun getCliente(codigo: String, empresa: String): Flow<Cliente> = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries
                .getCliente(
                    codigo = codigo,
                    empresa = empresa
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    suspend fun addCliente(cliente: Cliente) = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries.addCliente(cliente)
        }
    }.await()
}
