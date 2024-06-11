package com.clo.accloss.bills.data.repository

import com.clo.accloss.R
import com.clo.accloss.bills.data.source.BillDataSource
import com.clo.accloss.bills.domain.mappers.toDatabase
import com.clo.accloss.bills.domain.mappers.toDomain
import com.clo.accloss.bills.domain.mappers.toUi
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.bills.presentation.model.BillDetails
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultBillRepository(
    override val billDataSource: BillDataSource,
    override val coroutineContext: CoroutineContext
) : BillRepository {
    override suspend fun getRemoteBills(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Bill>> {
        return withContext(coroutineContext) {
            when (
                val apiOperation = billDataSource.billRemote
                    .getSafeBills(
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
                    val data = apiOperation.data.bills.map { billItem ->
                        billItem.toDomain().copy(empresa = company)
                    }

                    addBills(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override fun getBills(
        company: String,
    ): Flow<RequestState<List<Bill>>> = flow {
        emit(RequestState.Loading)

        billDataSource.billLocal.getBills(company)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("BILLS REPOSITORY: getBills")
            }
            .collect { cachedList ->
                emit(
                    RequestState.Success(
                        data = cachedList.map { billEntity ->
                            billEntity.toDomain()
                        }
                    )
                )
            }
    }.flowOn(coroutineContext)

    override fun getBillsBySalesman(
        company: String,
        salesman: String
    ): Flow<RequestState<List<Bill>>> = flow {
        emit(RequestState.Loading)

        billDataSource.billLocal.getBillsBySalesman(
            company = company,
            salesman = salesman
        ).catch { e ->
            emit(
                RequestState.Error(
                    message = DB_ERROR_MESSAGE
                )
            )
            e.log("BILLS REPOSITORY: getBillsBySalesman")
        }.collect { cachedList ->
            emit(
                RequestState.Success(
                    data = cachedList.map { billEntity ->
                        billEntity.toDomain()
                    }
                )
            )
        }
    }.flowOn(coroutineContext)

    override suspend fun getBill(
        bill: String,
        company: String
    ): RequestState<Bill> {
        return withContext(coroutineContext) {
            val billEntity = billDataSource.billLocal
                .getBill(
                    document = bill,
                    company = company,
                )
            if (billEntity != null) {
                RequestState.Success(
                    data = billEntity.toDomain()
                )
            } else {
                RequestState.Error(
                    message = R.string.this_bill_doesn_t_exists
                )
            }
        }
    }

    override fun getBillWithLines(
        bill: String,
        company: String,
    ): Flow<RequestState<BillDetails>> = flow {
        emit(RequestState.Loading)

        billDataSource.billLocal.getBillWithLines(
            company = company,
            bill = bill
        ).catch { e ->
            emit(
                RequestState.Error(
                    message = DB_ERROR_MESSAGE
                )
            )
            e.log("BILLS REPOSITORY: getBillsWithLines")
        }.collect { cachedList ->
            emit(
                RequestState.Success(
                    data = cachedList.toUi()
                )
            )
        }
    }.flowOn(coroutineContext)

    override suspend fun addBills(bills: List<Bill>) {
        withContext(coroutineContext) {
            billDataSource.billLocal.addBills(
                bills = bills.map { bill ->
                    bill.toDatabase()
                }
            )
        }
    }

    override suspend fun deleteBills(company: String) {
        withContext(coroutineContext) {
            billDataSource.billLocal.deleteBills(company = company)
        }
    }
}
