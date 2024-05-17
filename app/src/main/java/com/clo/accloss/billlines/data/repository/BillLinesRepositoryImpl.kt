package com.clo.accloss.billlines.data.repository

import com.clo.accloss.billlines.data.source.BillLinesDataSource
import com.clo.accloss.billlines.domain.mappers.toDatabase
import com.clo.accloss.billlines.domain.mappers.toDomain
import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.billlines.domain.repository.BillLinesRepository
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BillLinesRepositoryImpl(
    override val billLinesDataSource: BillLinesDataSource
) : BillLinesRepository {
    override suspend fun getRemoteBillLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<BillLines>> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = billLinesDataSource.billLinesRemote
                    .getSafeBillLines(
                        baseUrl = baseUrl,
                        document = document
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data.billLines.map { billLinesItem ->
                        billLinesItem.toDomain().copy(empresa = company)
                    }

                    addBillLines(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override fun getBillLines(
        document: String,
        company: String,
    ): Flow<RequestState<List<BillLines>>> = flow {
        emit(RequestState.Loading)
        billLinesDataSource.billLinesLocal.getBillLines(
            document = document,
            company = company
        ).catch { e ->
            emit(RequestState.Error(message = DB_ERROR_MESSAGE))
            e.log("BILL LINES REPOSITORY: getBillLines")
        }.collect { cachedList ->
            emit(
                RequestState.Success(
                    data = cachedList.map { billLinesEntity ->
                        billLinesEntity.toDomain()
                    }
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addBillLines(billLines: List<BillLines>) =
        withContext(Dispatchers.IO) {
            billLinesDataSource.billLinesLocal.addBillLines(
                billLines = billLines.map { line ->
                    line.toDatabase()
                }
            )
        }
}
