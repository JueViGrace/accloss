package com.clo.accloss.order.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.order.data.source.OrderDataSource
import com.clo.accloss.order.domain.mappers.toDatabase
import com.clo.accloss.order.domain.mappers.toDomain
import com.clo.accloss.order.domain.mappers.toUi
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.order.presentation.model.OrderDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class OrderRepositoryImpl(
    override val orderDataSource: OrderDataSource,
    override val coroutineContext: CoroutineContext
) : OrderRepository {
    override suspend fun getRemoteOrders(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Order>> {
        return withContext(coroutineContext) {
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
    }.flowOn(coroutineContext)

    override fun getOrdersBySalesman(
        company: String,
        salesman: String,
    ): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)

        orderDataSource.orderLocal
            .getOrdersBySalesman(
                company = company,
                salesman = salesman
            )
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
    }.flowOn(coroutineContext)

    override fun getOrderWithLines(
        order: String,
        company: String,
    ): Flow<RequestState<OrderDetails>> = flow {
        emit(RequestState.Loading)

        orderDataSource.orderLocal
            .getOrderWithLines(
                order = order,
                company = company
            )
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("ORDER REPOSITORY: getOrderWithLines")
            }
            .collect { getOrderWithLines ->
                emit(
                    RequestState.Success(
                        data = getOrderWithLines.toUi()
                    )
                )
            }
    }.flowOn(coroutineContext)

    override suspend fun getOrder(
        order: String,
        company: String
    ): RequestState<Order> = withContext(coroutineContext) {
        val orderEntity = orderDataSource.orderLocal.getOrder(
            order = order,
            company = company
        )

        if (orderEntity != null) {
            RequestState.Success(
                data = orderEntity.toDomain()
            )
        } else {
            RequestState.Error(
                message = "This order doesn't exists"
            )
        }
    }

    override suspend fun addOrder(orders: List<Order>) {
        orderDataSource.orderLocal.addOrder(
            orders = orders.map { order ->
                order.toDatabase()
            }
        )
    }
}
