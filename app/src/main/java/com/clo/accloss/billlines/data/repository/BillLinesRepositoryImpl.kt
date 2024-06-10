package com.clo.accloss.billlines.data.repository

import com.clo.accloss.billlines.data.source.BillLinesDataSource
import com.clo.accloss.billlines.domain.mappers.toDatabase
import com.clo.accloss.billlines.domain.mappers.toDomain
import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.billlines.domain.repository.BillLinesRepository
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BillLinesRepositoryImpl(
    override val billLinesDataSource: BillLinesDataSource,
    override val coroutineContext: CoroutineContext
) : BillLinesRepository {
    override suspend fun getRemoteBillLines(
        baseUrl: String,
        document: String,
        company: String
    ): RequestState<List<BillLines>> {
        return withContext(coroutineContext) {
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

    override suspend fun getBillLines(
        document: String,
        company: String,
    ): List<BillLines> {
        return withContext(coroutineContext) {
            billLinesDataSource
                .billLinesLocal
                .getBillLines(
                    document = document,
                    company = company
                ).map { billLinesEntity ->
                    billLinesEntity.toDomain()
                }
        }
    }

    override suspend fun addBillLines(billLines: List<BillLines>) {
        withContext(coroutineContext) {
            billLinesDataSource.billLinesLocal.addBillLines(
                billLines = billLines.map { line ->
                    line.toDatabase()
                }
            )
        }
    }
}
