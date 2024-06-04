package com.clo.accloss.orderlines.data.local

import com.clo.accloss.Lineas_pedido as OrderLinesEntity

interface OrderLinesLocal {
    suspend fun getOrderLines(
        document: String,
        company: String
    ): List<OrderLinesEntity>

    suspend fun addOrderLines(orderLines: List<OrderLinesEntity>)
}
