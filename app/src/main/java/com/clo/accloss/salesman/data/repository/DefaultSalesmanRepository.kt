package com.clo.accloss.salesman.data.repository

import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.salesman.data.source.SalesmanDataSource
import com.clo.accloss.salesman.domain.mappers.toDatabase
import com.clo.accloss.salesman.domain.mappers.toDomain
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultSalesmanRepository(
    override val salesmanDataSource: SalesmanDataSource,
    override val coroutineContext: CoroutineContext
) : SalesmanRepository {
    override suspend fun getRemoteSalesman(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Salesman>> {
        return withContext(coroutineContext) {
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
        return withContext(coroutineContext) {
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
        user: String,
        company: String,
    ): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        salesmanDataSource.salesmanLocal.getSalesmen(company)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
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
    }.flowOn(coroutineContext)

    override fun getSalesman(
        salesman: String,
        company: String
    ): Flow<RequestState<Salesman>> = flow {
        emit(RequestState.Loading)

        salesmanDataSource.salesmanLocal.getSalesman(
            salesman = salesman,
            company = company
        )
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("SALESMAN REPOSITORY: getSalesman")
            }
            .collect { salesmanEntity ->
                if (salesmanEntity != null) {
                    emit(
                        RequestState.Success(
                            data = salesmanEntity.toDomain()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.this_salesman_doesn_t_exists
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun addSalesmen(salesmen: List<Salesman>) {
        withContext(coroutineContext) {
            salesmanDataSource.salesmanLocal.addSalesmen(
                salesmen = salesmen.map { salesman ->
                    salesman.toDatabase()
                }
            )
        }
    }

    override suspend fun deleteSalesmen(company: String) {
        withContext(coroutineContext) {
            salesmanDataSource.salesmanLocal.deleteSalesmen(company = company)
        }
    }
}
