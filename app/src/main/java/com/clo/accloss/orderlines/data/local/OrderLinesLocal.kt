package com.clo.accloss.orderlines.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Lineas_pedido as OrderLinesEntity

interface OrderLinesLocal {
    suspend fun getOrderLines(
        document: String,
        company: String
    ): Flow<List<OrderLinesEntity>>

    suspend fun addOrderLines(orderLines: List<OrderLinesEntity>)
}
