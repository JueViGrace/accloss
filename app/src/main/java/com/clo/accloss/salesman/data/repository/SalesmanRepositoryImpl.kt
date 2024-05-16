package com.clo.accloss.salesman.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.salesman.data.source.SalesmanDataSource
import com.clo.accloss.salesman.domain.mappers.toDatabase
import com.clo.accloss.salesman.domain.mappers.toDomain
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SalesmanRepositoryImpl(
    override val salesmanDataSource: SalesmanDataSource
) : SalesmanRepository {
    override suspend fun getRemoteSalesman(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Salesman>> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = salesmanDataSource.salesmanRemote
                    .getSafeSalesman(
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
                    val data = apiOperation.data.map { salesmanResponse ->
                        salesmanResponse.toDomain().copy(empresa = company)
                    }

                    addSalesmen(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override suspend fun getRemoteMasters(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Salesman>> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = salesmanDataSource.salesmanRemote
                    .getSafeMasters(
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
                    val data = apiOperation.data.map { salesmanResponse ->
                        salesmanResponse.toDomain().copy(empresa = company)
                    }

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override fun getSalesmen(
        baseUrl: String,
        user: String,
        company: String,
    ): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        salesmanDataSource.salesmanLocal.getSalesmen(company)
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("SALESMAN REPOSITORY: getSalesmen")
            }
            .collect { cachedList ->

                emit(
                    RequestState.Success(
                        data = cachedList.map { salesman ->
                            salesman.toDomain()
                        }
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addSalesmen(salesmen: List<Salesman>) =
        withContext(Dispatchers.IO) {
            salesmanDataSource.salesmanLocal.addSalesmen(
                salesmen = salesmen.map { salesman ->
                    salesman.toDatabase()
                }
            )
        }
}
