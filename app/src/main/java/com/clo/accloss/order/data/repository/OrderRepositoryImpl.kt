package com.clo.accloss.order.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.order.data.source.OrderDataSource
import com.clo.accloss.order.domain.mappers.toDatabase
import com.clo.accloss.order.domain.mappers.toDomain
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.order.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class OrderRepositoryImpl(
    override val orderDataSource: OrderDataSource
) : OrderRepository {
    override suspend fun getRemoteOrders(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Order>> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = orderDataSource.orderRemote
                    .getSafeOrders(
                        baseUrl = baseUrl,
                        user = user
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error

                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data.orders.map { orderItem ->
                        orderItem.toDomain().copy(empresa = company)
                    }

                    addOrder(data)

                    RequestState.Success(
                        data = data

                    )
                }
            }
        }
    }

    override fun getOrders(
        company: String,
    ): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)

        orderDataSource.orderLocal.getOrders(company)
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("ORDER REPOSITORY: getOrders")
            }
            .collect { cachedList ->
                emit(
                    RequestState.Success(
                        data = cachedList.map { orderEntity ->
                            orderEntity.toDomain()
                        }
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override fun getOrder(
        order: String,
        company: String
    ): Flow<RequestState<Order>> = flow {
        emit(RequestState.Loading)

        orderDataSource.orderLocal.getOrder(
            order = order,
            company = company
        )
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("ORDER REPOSITORY: getOrder")
            }
            .collect { orderEntity ->
                emit(
                    RequestState.Success(
                        data = orderEntity.toDomain()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addOrder(orders: List<Order>) =
        withContext(Dispatchers.IO) {
            orderDataSource.orderLocal.addOrder(
                orders = orders.map { order ->
                    order.toDatabase()
                }
            )
        }
}
