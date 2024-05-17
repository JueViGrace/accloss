package com.clo.accloss.order.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Pedido as OrderEntity

interface OrderLocal {
    suspend fun getOrders(company: String): Flow<List<OrderEntity>>

    suspend fun getOrder(
        order: String,
        company: String
    ): Flow<OrderEntity>

    suspend fun addOrder(orders: List<OrderEntity>)
}
