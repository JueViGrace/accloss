package com.clo.accloss.order.data.local

import com.clo.accloss.GetOrderWithLines
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Pedido as OrderEntity

interface OrderLocal {
    suspend fun getOrders(company: String): Flow<List<OrderEntity>>

    suspend fun getOrdersBySalesman(
        company: String,
        salesman: String
    ): Flow<List<OrderEntity>>

    suspend fun getOrderWithLines(
        company: String,
        order: String
    ): Flow<List<GetOrderWithLines>>

    suspend fun getOrder(
        order: String,
        company: String
    ): OrderEntity?

    suspend fun addOrder(orders: List<OrderEntity>)
}
