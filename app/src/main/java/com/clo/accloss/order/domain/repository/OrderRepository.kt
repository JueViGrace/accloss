package com.clo.accloss.order.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.order.data.source.OrderDataSource
import com.clo.accloss.order.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    val orderDataSource: OrderDataSource

    suspend fun getRemoteOrders(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Order>>

    fun getOrders(
        company: String,
    ): Flow<RequestState<List<Order>>>

    fun getOrder(
        order: String,
        company: String
    ): Flow<RequestState<Order>>

    suspend fun addOrder(orders: List<Order>)
}
