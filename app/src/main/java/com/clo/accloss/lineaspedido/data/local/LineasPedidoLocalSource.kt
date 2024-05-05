package com.clo.accloss.lineaspedido.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Lineas_pedido as LineasPedidoEntity

class LineasPedidoLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getLineasPedido(
        documento: String,
        empresa: String
    ): Flow<List<LineasPedidoEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasPedidoQueries.getLineasPedido(
                documento = documento,
                empresa = empresa
            )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }
    }.await()

    suspend fun addLineasPedido(lineasPedido: LineasPedidoEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasPedidoQueries.addLineasPedido(lineasPedido)
        }
    }.await()
}
