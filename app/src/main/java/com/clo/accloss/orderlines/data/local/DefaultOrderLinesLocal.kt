package com.clo.accloss.orderlines.data.local

import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import com.clo.accloss.Lineas_pedido as OrderLinesEntity

class DefaultOrderLinesLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : OrderLinesLocal {
    override suspend fun getOrderLines(
        document: String,
        company: String
    ): List<OrderLinesEntity> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.lineasPedidoQueries.getLineasPedido(
                    documento = document,
                    empresa = company
                ).executeAsList()
            }
        }.await()
    }

    override suspend fun addOrderLines(orderLines: List<OrderLinesEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.lineasPedidoQueries.transaction {
                    orderLines.forEach { line ->
                        db.lineasPedidoQueries.addLineasPedido(line)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteOrderLines(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.lineasPedidoQueries.transaction {
                        db.lineasPedidoQueries.deleteLineasPedido(empresa = company)

                }
            }
        }.await()
    }
}
