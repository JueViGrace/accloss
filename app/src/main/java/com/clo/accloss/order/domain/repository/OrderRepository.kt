package com.clo.accloss.order.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.order.data.source.OrderDataSource
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.order.presentation.model.OrderDetails
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface OrderRepository {
    val orderDataSource: OrderDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteOrders(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Order>>

    fun getOrders(
        company: String,
    ): Flow<RequestState<List<Order>>>

    fun getOrdersBySalesman(
        company: String,
        salesman: String
    ): Flow<RequestState<List<Order>>>

    fun getOrderWithLines(

        order: String,
        company: String
    ): Flow<RequestState<OrderDetails>>

    suspend fun getOrder(
        order: String,
        company: String
    ): RequestState<Order>

    suspend fun addOrder(orders: List<Order>)
}
