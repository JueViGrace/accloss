package com.clo.accloss.bills.domain.usecase

import com.clo.accloss.billlines.domain.repository.BillLinesRepository
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.bills.presentation.model.BillDetails
import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetBill(
    private val getCurrentUser: GetCurrentUser,
    private val billRepository: BillRepository,
    private val billLinesRepository: BillLinesRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(billId:String): Flow<RequestState<BillDetails>> = flow {
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
                        val bill = billRepository
                            .getBill(
                                bill = billId,
                                company = sessionResult.data.empresa
                            )

                        when (bill) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = bill.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                val billLines = billLinesRepository.getBillLines(
                                    document = billId,
                                    company = sessionResult.data.empresa
                                )

                                if (billLines.isEmpty()) {
                                    billLinesRepository.getRemoteBillLines(
                                        baseUrl = sessionResult.data.enlaceEmpresa,
                                        document = bill.data.documento,
                                        company = sessionResult.data.empresa
                                    )
                                }

                                billRepository.getBillWithLines(
                                    bill = billId,
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