package com.clo.accloss.orderlines.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.orderlines.data.local.OrderLinesLocalSource
import com.clo.accloss.orderlines.data.remote.source.OrderLinesRemoteSource
import com.clo.accloss.orderlines.domain.mappers.toDatabase
import com.clo.accloss.orderlines.domain.mappers.toDomain
import com.clo.accloss.orderlines.domain.model.OrderLines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrderLinesRepository(
    private val orderLinesRemoteSource: OrderLinesRemoteSource,
    private val orderLinesLocalSource: OrderLinesLocalSource
) {
    private fun getRemoteOrderLines(
        baseUrl: String,
        document: String,
        company: String
    ): Flow<RequestState<List<OrderLines>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = orderLinesRemoteSource
            .getSafeOrderLines(
                baseUrl = baseUrl,
                document = document
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error.message ?: Constants.SERVER_ERROR
                    )
                )
            }
            is ApiOperation.Success -> {
                emit(
                    RequestState.Success(
                        data = apiOperation.data.orderLines.map { orderLinesItem ->
                            orderLinesItem.toDomain().copy(empresa = company)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getOrderLines(
        baseUrl: String,
        document: String,
        company: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<OrderLines>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        orderLinesLocalSource.getOrderLines(
            document = document,
            company = company
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { orderLinesEntity ->
                                orderLinesEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteOrderLines(
                        baseUrl = baseUrl,
                        document = document,
                        company = company
                    )
                        .collect { result ->
                            when (result) {
                                is RequestState.Error -> {
                                    emit(
                                        RequestState.Error(
                                            message = result.message
                                        )
                                    )
                                }
                                is RequestState.Success -> {
                                    addOrderLines(result.data)
                                }
                                else -> emit(RequestState.Loading)
                            }
                        }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addOrderLines(orderLines: List<OrderLines>) =
        orderLinesLocalSource.addOrderLines(
            orderLines = orderLines.map { line ->
                line.toDatabase()
            }
        )
}
