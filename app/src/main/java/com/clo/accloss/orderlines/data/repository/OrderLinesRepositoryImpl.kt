package com.clo.accloss.orderlines.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.orderlines.data.source.OrderLinesDataSource
import com.clo.accloss.orderlines.domain.mappers.toDatabase
import com.clo.accloss.orderlines.domain.mappers.toDomain
import com.clo.accloss.orderlines.domain.model.OrderLines
import com.clo.accloss.orderlines.domain.repository.OrderLinesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class OrderLinesRepositoryImpl(
    override val orderLinesDataSource: OrderLinesDataSource
) : OrderLinesRepository {
    override suspend fun getRemoteOrderLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<OrderLines>> {
        return withContext(Dispatchers.IO) {
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

    override fun getOrderLines(
        document: String,
        company: String,
    ): Flow<RequestState<List<OrderLines>>> = flow {
        emit(RequestState.Loading)

        orderLinesDataSource.orderLinesLocal.getOrderLines(
            document = document,
            company = company
        ).catch { e ->
            emit(RequestState.Error(message = DB_ERROR_MESSAGE))
            e.log("ORDER LINES REPOSITORY: getOrderLines")
        }.collect { cachedList ->
            emit(
                RequestState.Success(
                    data = cachedList.map { orderLinesEntity ->
                        orderLinesEntity.toDomain()
                    }
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addOrderLines(orderLines: List<OrderLines>) =
        withContext(Dispatchers.IO) {
            orderLinesDataSource.orderLinesLocal.addOrderLines(
                orderLines = orderLines.map { line ->
                    line.toDatabase()
                }
            )
        }
}
