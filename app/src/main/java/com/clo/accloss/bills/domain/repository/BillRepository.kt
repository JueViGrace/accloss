package com.clo.accloss.bills.domain.repository

import android.util.Log
import com.clo.accloss.bills.data.local.BillLocalSource
import com.clo.accloss.bills.data.remote.source.BillRemoteSource
import com.clo.accloss.bills.domain.mappers.toDatabase
import com.clo.accloss.bills.domain.mappers.toDomain
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BillRepository(
    private val billRemoteSource: BillRemoteSource,
    private val billLocalSource: BillLocalSource
) {
    fun getRemoteBills(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Bill>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = billRemoteSource
            .getSafeBills(
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
                val data = apiOperation.data.bills.map { billItem ->
                    billItem.toDomain().copy(empresa = company)
                }

                addBills(data)

                emit(
                    RequestState.Success(
                        data = data
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getBills(
        baseUrl: String,
        user: String,
        company: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Bill>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        billLocalSource.getBills(company)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { billEntity ->
                                billEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteBills(
                        baseUrl = baseUrl,
                        user = user,
                        company = company
                    )
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
                                    Log.i("Force reload Bills", "getBills: ${result.data}")
                                }
                                else -> emit(RequestState.Loading)
                            }
                        }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addBills(bills: List<Bill>) =
        billLocalSource.addBills(
            bills = bills.map { bill ->
                bill.toDatabase()
            }
        )
}
