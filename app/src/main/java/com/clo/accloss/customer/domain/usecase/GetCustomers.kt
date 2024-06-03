package com.clo.accloss.customer.domain.usecase

import com.clo.accloss.core.common.Constants.UNEXPECTED_ERROR
import com.clo.accloss.core.common.log
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.customer.domain.model.Customer
import com.clo.accloss.customer.domain.repository.CustomerRepository
import com.clo.accloss.customer.presentation.model.CustomerData
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetCustomers(
    private val getCurrentUser: GetCurrentUser,
    private val customerRepository: CustomerRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(): Flow<RequestState<List<CustomerData>>> = flow {
        emit(RequestState.Loading)

        getCurrentUser()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = UNEXPECTED_ERROR
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
                        customerRepository
                            .getCustomersData(
                                company = sessionResult.data.empresa
                            )
                            .catch { e ->
                                emit(
                                    RequestState.Error(
                                        message = UNEXPECTED_ERROR
                                    )
                                )
                                e.log("GET CUSTOMERS USE CASE: customer")
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
                    else -> emit(RequestState.Loading)
                }
            }
    }.flowOn(coroutineContext)
}
