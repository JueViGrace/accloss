package com.clo.accloss.order.domain.usecase

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.log
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.order.presentation.model.OrderDetails
import com.clo.accloss.orderlines.domain.repository.OrderLinesRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetOrder(
    private val getCurrentUser: GetCurrentUser,
    private val orderRepository: OrderRepository,
    private val orderLinesRepository: OrderLinesRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(orderId: String): Flow<RequestState<OrderDetails>> = flow {
        emit(RequestState.Loading)
        getCurrentUser()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = Constants.UNEXPECTED_ERROR
                    )
                )
                e.log("GET ORDER USE CASE: session")
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
                        val order = orderRepository
                            .getOrder(
                                order = orderId,
                                company = sessionResult.data.empresa
                            )

                        when (order) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = order.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                val orderLines = orderLinesRepository.getOrderLines(
                                    document = orderId,
                                    company = sessionResult.data.empresa
                                )

                                if (orderLines.isEmpty()) {
                                    orderLinesRepository.getRemoteOrderLines(
                                        baseUrl = sessionResult.data.enlaceEmpresa,
                                        document = order.data.ktiNdoc,
                                        company = sessionResult.data.empresa
                                    )
                                }

                                orderRepository.getOrderWithLines(
                                    orderId,
                                    company = sessionResult.data.empresa
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
                            else -> emit(RequestState.Loading)
                        }
                    }
                    else -> emit(RequestState.Loading)
                }
            }
    }.flowOn(coroutineContext)
}
