package com.clo.accloss.orderlines.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.orderlines.data.source.OrderLinesDataSource
import com.clo.accloss.orderlines.domain.model.OrderLines
import kotlinx.coroutines.flow.Flow

interface OrderLinesRepository {
    val orderLinesDataSource: OrderLinesDataSource

    suspend fun getRemoteOrderLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<OrderLines>>

    fun getOrderLines(
        document: String,
        company: String,
    ): Flow<RequestState<List<OrderLines>>>

    suspend fun addOrderLines(orderLines: List<OrderLines>)
}
