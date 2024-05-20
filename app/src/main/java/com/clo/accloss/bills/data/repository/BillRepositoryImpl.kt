package com.clo.accloss.bills.data.repository

import com.clo.accloss.bills.data.source.BillDataSource
import com.clo.accloss.bills.domain.mappers.toDatabase
import com.clo.accloss.bills.domain.mappers.toDomain
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.domain.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BillRepositoryImpl(
    override val billDataSource: BillDataSource
) : BillRepository {
    override suspend fun getRemoteBills(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Bill>> {
        return withContext(Dispatchers.IO) {
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
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
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
    }.flowOn(Dispatchers.IO)

    override suspend fun addBills(bills: List<Bill>) =
        withContext(Dispatchers.IO) {
            billDataSource.billLocal.addBills(
                bills = bills.map { bill ->
                    bill.toDatabase()
                }
            )
        }
}
