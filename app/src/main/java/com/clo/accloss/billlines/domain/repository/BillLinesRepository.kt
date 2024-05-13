package com.clo.accloss.billlines.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.billlines.data.local.BillLinesLocalSource
import com.clo.accloss.billlines.data.remote.source.BillLinesRemoteSource
import com.clo.accloss.billlines.domain.mappers.toDatabase
import com.clo.accloss.billlines.domain.mappers.toDomain
import com.clo.accloss.billlines.domain.model.BillLines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BillLinesRepository(
    private val billLinesRemoteSource: BillLinesRemoteSource,
    private val billLinesLocalSource: BillLinesLocalSource
) {
    private fun getRemoteBillLines(
        baseUrl: String,
        document: String,
        company: String
    ): Flow<RequestState<List<BillLines>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = billLinesRemoteSource
            .getSafeBillLines(
                baseUrl = baseUrl,
                document = document
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
                emit(
                    RequestState.Success(
                        data = apiOperation.data.billLines.map { billLinesItem ->
                            billLinesItem.toDomain().copy(empresa = company)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getBillLines(
        baseUrl: String,
        document: String,
        company: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<BillLines>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        billLinesLocalSource.getBillLines(
            document = document,
            company = company
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { billLinesEntity ->
                                billLinesEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteBillLines(
                        baseUrl = baseUrl,
                        document = document,
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
                                    addBillLines(result.data)
                                }
                                else -> emit(RequestState.Loading)
                            }
                        }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addBillLines(billLines: List<BillLines>) =
        billLinesLocalSource.addBillLines(
            billLines = billLines.map { line ->
                line.toDatabase()
            }
        )
}
