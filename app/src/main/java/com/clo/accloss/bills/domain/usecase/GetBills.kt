package com.clo.accloss.bills.domain.usecase

import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.core.common.Constants.UNEXPECTED_ERROR
import com.clo.accloss.core.common.log
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetBills(
    private val getCurrentUser: GetCurrentUser,
    private val billRepository: BillRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(id: String): Flow<RequestState<List<Bill>>> = flow {
        emit(RequestState.Loading)

        getCurrentUser()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = UNEXPECTED_ERROR
                    )
                )
                e.log("GET BILLS USE CASE: session")
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
                        if (id.isEmpty()) {
                            billRepository.getBills(
                                company = sessionResult.data.empresa
                            ).catch { e ->
                                emit(
                                    RequestState.Error(
                                        message = UNEXPECTED_ERROR
                                    )
                                )
                                e.log("GET BILLS USE CASE: bills")
                            }.collect { result ->
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
                        } else {
                            billRepository.getBillsBySalesman(
                                company = sessionResult.data.empresa,
                                salesman = id
                            ).catch { e ->
                                emit(
                                    RequestState.Error(
                                        message = UNEXPECTED_ERROR
                                    )
                                )
                                e.log("GET BILLS USE CASE: bills")
                            }.collect { result ->
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
                    else -> emit(RequestState.Loading)
                }
            }
    }.flowOn(coroutineContext)
}
