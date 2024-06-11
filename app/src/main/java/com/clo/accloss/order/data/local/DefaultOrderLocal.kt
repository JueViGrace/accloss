package com.clo.accloss.order.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.GetOrderWithLines
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Pedido as OrderEntity

class DefaultOrderLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : OrderLocal {
    override suspend fun getOrders(company: String): Flow<List<OrderEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.pedidoQueries
                    .getPedidos(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getOrdersBySalesman(
        company: String,
        salesman: String,
    ): Flow<List<OrderEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.pedidoQueries
                    .getOrdersBySalesman(
                        empresa = company,
                        vendedor = salesman
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getOrderWithLines(
        company: String,
        order: String,
    ): Flow<List<GetOrderWithLines>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.pedidoQueries
                    .getOrderWithLines(
                        pedido = order,
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getOrder(
        order: String,
        company: String
    ): OrderEntity? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.pedidoQueries
                    .getPedido(
                        pedido = order,
                        empresa = company
                    )
                    .executeAsOneOrNull()
            }
        }.await()
    }

    override suspend fun addOrder(orders: List<OrderEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.pedidoQueries.transaction {
                    orders.forEach { order ->
                        db.pedidoQueries.addPedido(order)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteOrders(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.pedidoQueries.transaction {
                    db.pedidoQueries.deletePedidos(empresa = company)
                }
            }
        }.await()
    }
}
