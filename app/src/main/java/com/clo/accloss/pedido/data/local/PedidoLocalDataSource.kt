package com.clo.accloss.pedido.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Pedido as PedidoEntity

class PedidoLocalDataSource(
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
        }
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
        }
    }.await()

    suspend fun addPedido(pedido: PedidoEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries.addPedido(pedido)
        }
    }.await()

    suspend fun deletePedidos(empresa: String) = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries.deletePedidos(empresa)
        }
    }.await()
}
