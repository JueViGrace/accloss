package com.clo.accloss.cliente.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.Cliente
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ClienteLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getClientes(empresa: String): Flow<List<Cliente>> = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries
                .getClientes(empresa = empresa)
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
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
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addCliente(clientes: List<Cliente>) = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries.transaction {
                clientes.forEach { cliente ->
                    db.clienteQueries.addCliente(cliente)
                }
            }
        }
    }.await()
}
