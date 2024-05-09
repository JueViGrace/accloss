package com.clo.accloss.vendedor.domain.repository

import com.clo.accloss.core.common.Constants.GERENCIAS
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.vendedor.data.local.VendedorLocalSource
import com.clo.accloss.vendedor.data.remote.source.VendedorRemoteSource
import com.clo.accloss.vendedor.domain.mappers.toDatabase
import com.clo.accloss.vendedor.domain.mappers.toDomain
import com.clo.accloss.vendedor.domain.model.Vendedor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class VendedorRepository(
    private val vendedorRemoteSource: VendedorRemoteSource,
    private val vendedorLocalSource: VendedorLocalSource
) {
    fun getRemoteVendedor(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Vendedor>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = vendedorRemoteSource
            .getSafeVendedor(
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
                        data = apiOperation.data.map { vendedorResponse ->
                            vendedorResponse.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getRemoteCoordinaciones(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Vendedor>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = vendedorRemoteSource
            .getSafeCoordinaciones(
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
                        data = apiOperation.data.map { vendedorResponse ->
                            vendedorResponse.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getVendedores(
        baseUrl: String,
        user: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Vendedor>>> = flow<RequestState<List<Vendedor>>> {
        emit(RequestState.Loading)

        var reload = forceReload

        vendedorLocalSource.getVendedores(empresa).collect { cachedList ->
            if (cachedList.isNotEmpty() && !reload) {
                emit(
                    RequestState.Success(
                        data = cachedList.map { vendedor ->
                            vendedor.toDomain()
                        }
                    )
                )
            } else {
                getRemoteVendedor(
                    baseUrl = baseUrl,
                    user = user,
                    empresa = empresa
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
                            addVendedor(result.data)
                        }
                        else -> emit(RequestState.Loading)
                    }
                }
                if (GERENCIAS.contains(user)) {
                    getRemoteCoordinaciones(
                        baseUrl = baseUrl,
                        user = user,
                        empresa = empresa
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
                                addVendedor(result.data)
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                }
                reload = false
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun addVendedor(vendedores: List<Vendedor>) =
        vendedorLocalSource.addVendedor(
            vendedores = vendedores.map { vendedor ->
                vendedor.toDatabase()
            }
        )
}
