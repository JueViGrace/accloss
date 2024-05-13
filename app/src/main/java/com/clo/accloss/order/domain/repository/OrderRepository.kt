package com.clo.accloss.order.domain.repository

import android.util.Log
import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.order.data.local.OrderLocalSource
import com.clo.accloss.order.data.remote.source.OrderRemoteSource
import com.clo.accloss.order.domain.mappers.toDatabase
import com.clo.accloss.order.domain.mappers.toDomain
import com.clo.accloss.order.domain.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrderRepository(
    private val orderRemoteSource: OrderRemoteSource,
    private val orderLocalSource: OrderLocalSource
) {
    fun getRemoteOrders(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = orderRemoteSource
            .getSafeOrders(
                baseUrl = baseUrl,
                user = user
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error
                    )
                )
            }
            is ApiOperation.Success -> {
                val data = apiOperation.data.orders.map { orderItem ->
                    orderItem.toDomain().copy(empresa = company)
                }

                addOrder(data)

                emit(
                    RequestState.Success(
                        data = data
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getOrders(
        baseUrl: String,
        user: String,
        company: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        orderLocalSource.getOrders(company)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { orderEntity ->
                                orderEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteOrders(
                        baseUrl = baseUrl,
                        user = user,
                        company = company
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = result.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                Log.i("Force reload Orders", "getOrders: ${result.data}")
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    fun getPedido(
        order: String,
        company: String
    ): Flow<RequestState<Order>> = flow<RequestState<Order>> {
        emit(RequestState.Loading)

        orderLocalSource.getOrder(
            order = order,
            company = company
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { orderEntity ->
                emit(
                    RequestState.Success(
                        data = orderEntity.toDomain()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addOrder(orders: List<Order>) =
        orderLocalSource.addOrder(
            orders = orders.map { order ->
                order.toDatabase()
            }
        )
}
