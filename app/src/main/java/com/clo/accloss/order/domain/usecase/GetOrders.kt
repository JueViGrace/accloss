package com.clo.accloss.order.domain.usecase

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.log
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetOrders(
    private val getCurrentUser: GetCurrentUser,
    private val ordersRepository: OrderRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(id: String): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)

        getCurrentUser()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = Constants.UNEXPECTED_ERROR
                    )
                )
                e.log("GET CUSTOMERS USE CASE: session")
            }
            .collect { sessionResult ->
                when (sessionResult) {
                    is RequestState.Error -> {
                        emit(
                            RequestState.Error(
                                message = sessionResult.message
                            )
                        )
                    }
                    is RequestState.Success -> {
                        when {
                            id.isEmpty() -> {
                                ordersRepository
                                    .getOrders(
                                        company = sessionResult.data.empresa
                                    )
                                    .catch { e ->
                                        emit(
                                            RequestState.Error(
                                                message = Constants.UNEXPECTED_ERROR
                                            )
                                        )
                                        e.log("GET ORDERS USE CASE: orders, id empty")
                                    }
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
                                                emit(
                                                    RequestState.Success(
                                                        data = result.data
                                                    )
                                                )
                                            }
                                            else -> emit(RequestState.Loading)
                                        }
                                    }
                            }

                            id.isNotEmpty() -> {
                                ordersRepository
                                    .getOrdersBySalesman(
                                        company = sessionResult.data.empresa,
                                        salesman = id
                                    )
                                    .catch { e ->
                                        emit(
                                            RequestState.Error(
                                                message = Constants.UNEXPECTED_ERROR
                                            )
                                        )
                                        e.log("GET ORDERS USE CASE: orders, id not empty")
                                    }
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
                                                emit(
                                                    RequestState.Success(
                                                        data = result.data
                                                    )
                                                )
                                            }
                                            else -> emit(RequestState.Loading)
                                        }
                                    }
                            }
                        }
                    }
                    else -> emit(RequestState.Loading)
                }
            }
    }.flowOn(coroutineContext)
}
