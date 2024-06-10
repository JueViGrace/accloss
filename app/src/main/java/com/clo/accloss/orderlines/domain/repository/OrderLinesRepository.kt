package com.clo.accloss.orderlines.domain.repository

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.orderlines.data.source.OrderLinesDataSource
import com.clo.accloss.orderlines.domain.model.OrderLines
import kotlin.coroutines.CoroutineContext

interface OrderLinesRepository {
    val orderLinesDataSource: OrderLinesDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteOrderLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<OrderLines>>

    suspend fun getOrderLines(
        document: String,
        company: String,
    ): List<OrderLines>

    suspend fun addOrderLines(orderLines: List<OrderLines>)
}
