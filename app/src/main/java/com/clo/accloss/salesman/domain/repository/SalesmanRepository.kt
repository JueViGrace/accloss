package com.clo.accloss.salesman.domain.repository

import android.util.Log
import com.clo.accloss.core.common.Constants.MASTERS
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.salesman.data.local.SalesmanLocalSource
import com.clo.accloss.salesman.data.remote.source.SalesmanRemoteSource
import com.clo.accloss.salesman.domain.mappers.toDatabase
import com.clo.accloss.salesman.domain.mappers.toDomain
import com.clo.accloss.salesman.domain.model.Salesman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SalesmanRepository(
    private val salesmanRemoteSource: SalesmanRemoteSource,
    private val salesmanLocalSource: SalesmanLocalSource
) {
    /*fun getRemoteSalesman(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = salesmanRemoteSource
            .getSafeSalesman(
                baseUrl = baseUrl,
                user = user
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error.message ?: SERVER_ERROR
                    )
                )
            }
            is ApiOperation.Success -> {
                emit(
                    RequestState.Success(
                        data = apiOperation.data.map { salesmanResponse ->
                            salesmanResponse.toDomain().copy(empresa = company)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)*/

    suspend fun getRemoteSalesman(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = salesmanRemoteSource
            .getSafeSalesman(
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
                val data = apiOperation.data.map { salesmanResponse ->
                    salesmanResponse.toDomain().copy(empresa = company)
                }

                addSalesmen(data)

                emit(
                    RequestState.Success(
                        data = data
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getRemoteMasters(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = salesmanRemoteSource
            .getSafeMasters(
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
                val data = apiOperation.data.map { vendedorResponse ->
                    vendedorResponse.toDomain().copy(empresa = company)
                }

                addSalesmen(data)

                emit(
                    RequestState.Success(
                        data = data
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getSalesmen(
        baseUrl: String,
        user: String,
        company: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Salesman>>> = flow<RequestState<List<Salesman>>> {
        emit(RequestState.Loading)

        var reload = forceReload

        salesmanLocalSource.getSalesmen(company).collect { cachedList ->
            if (cachedList.isNotEmpty() && !reload) {
                emit(
                    RequestState.Success(
                        data = cachedList.map { salesman ->
                            salesman.toDomain()
                        }
                    )
                )
            } else {
                getRemoteSalesman(
                    baseUrl = baseUrl,
                    user = user,
                    company = company
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
                            Log.i("Force reload Salesman", "getSalesmen: ${result.data}")
                        }
                        else -> emit(RequestState.Loading)
                    }
                }
                if (MASTERS.contains(user)) {
                    getRemoteMasters(
                        baseUrl = baseUrl,
                        user = user,
                        company = company
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
                                Log.i("Force reload Salesman", "getSalesmen: ${result.data}")
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                }
                reload = false
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun addSalesmen(salesmen: List<Salesman>) =
        salesmanLocalSource.addSalesmen(
            salesmen = salesmen.map { salesman ->
                salesman.toDatabase()
            }
        )
}
