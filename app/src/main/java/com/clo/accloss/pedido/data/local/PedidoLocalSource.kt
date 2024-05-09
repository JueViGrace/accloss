package com.clo.accloss.pedido.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Pedido as PedidoEntity

class PedidoLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getPedidos(empresa: String): Flow<List<PedidoEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries
                .getPedidos(
                    empresa = empresa
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getPedido(
        pedido: String,
        empresa: String
    ): Flow<PedidoEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries
                .getPedido(
                    pedido = pedido,
                    empresa = empresa
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addPedido(pedidos: List<PedidoEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries.transaction {
                pedidos.forEach { pedido ->
                    db.pedidoQueries.addPedido(pedido)
                }
            }
        }
    }.await()
}
