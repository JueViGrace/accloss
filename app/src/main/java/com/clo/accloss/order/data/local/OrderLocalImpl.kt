package com.clo.accloss.order.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Pedido as OrderEntity

class OrderLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : OrderLocal {
    override suspend fun getOrders(company: String): Flow<List<OrderEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries
                .getPedidos(
                    empresa = company
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getOrder(
        order: String,
        company: String
    ): Flow<OrderEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries
                .getPedido(
                    pedido = order,
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addOrder(orders: List<OrderEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.pedidoQueries.transaction {
                orders.forEach { order ->
                    db.pedidoQueries.addPedido(order)
                }
            }
        }
    }.await()
}
