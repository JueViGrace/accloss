package com.clo.accloss.orderlines.data.repository

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.orderlines.data.source.OrderLinesDataSource
import com.clo.accloss.orderlines.domain.mappers.toDatabase
import com.clo.accloss.orderlines.domain.mappers.toDomain
import com.clo.accloss.orderlines.domain.model.OrderLines
import com.clo.accloss.orderlines.domain.repository.OrderLinesRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class OrderLinesRepositoryImpl(
    override val orderLinesDataSource: OrderLinesDataSource,
    override val coroutineContext: CoroutineContext
) : OrderLinesRepository {
    override suspend fun getRemoteOrderLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<OrderLines>> {
        return withContext(coroutineContext) {
            when (
                val apiOperation = orderLinesDataSource.orderLinesRemote
                    .getSafeOrderLines(
                        baseUrl = baseUrl,
                        document = document
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data.orderLines.map { orderLinesItem ->
                        orderLinesItem.toDomain().copy(empresa = company)
                    }

                    addOrderLines(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override suspend fun getOrderLines(
        document: String,
        company: String,
    ): List<OrderLines> = withContext(coroutineContext) {
        orderLinesDataSource.orderLinesLocal
            .getOrderLines(
                document = document,
                company = company
            ).map { orderLinesEntity ->
                orderLinesEntity.toDomain()
            }
    }

    override suspend fun addOrderLines(orderLines: List<OrderLines>) =
        withContext(coroutineContext) {
            orderLinesDataSource.orderLinesLocal.addOrderLines(
                orderLines = orderLines.map { line ->
                    line.toDatabase()
                }
            )
        }
}
