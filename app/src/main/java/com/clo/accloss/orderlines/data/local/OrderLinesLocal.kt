package com.clo.accloss.orderlines.data.local

import kotlinx.coroutines.CoroutineScope
import com.clo.accloss.Lineas_pedido as OrderLinesEntity

interface OrderLinesLocal {
    val scope: CoroutineScope

    suspend fun getOrderLines(
        document: String,
        company: String
    ): List<OrderLinesEntity>

    suspend fun addOrderLines(orderLines: List<OrderLinesEntity>)

    suspend fun deleteOrderLines(company: String)
}
