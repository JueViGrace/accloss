package com.clo.accloss.orderlines.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Lineas_pedido as OrderLinesEntity

class OrderLinesLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : OrderLinesLocal {
    override suspend fun getOrderLines(
        document: String,
        company: String
    ): List<OrderLinesEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasPedidoQueries.getLineasPedido(
                documento = document,
                empresa = company
            ).executeAsList()
        }
    }.await()

    override suspend fun addOrderLines(orderLines: List<OrderLinesEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasPedidoQueries.transaction {
                orderLines.forEach { line ->
                    db.lineasPedidoQueries.addLineasPedido(line)
                }
            }
        }
    }.await()
}
