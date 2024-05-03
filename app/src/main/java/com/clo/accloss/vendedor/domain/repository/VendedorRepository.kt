package com.clo.accloss.vendedor.domain.repository

import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.vendedor.data.local.VendedorLocalDataSource
import com.clo.accloss.vendedor.data.remote.source.VendedorRemoteDataSource
import com.clo.accloss.vendedor.domain.mappers.toDatabase
import com.clo.accloss.vendedor.domain.mappers.toDomain
import com.clo.accloss.vendedor.domain.model.Vendedor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class VendedorRepository(
    private val vendedorRemoteDataSource: VendedorRemoteDataSource,
    private val vendedorLocalDataSource: VendedorLocalDataSource
) {
    fun getRemoteVendedor(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Vendedor>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = vendedorRemoteDataSource
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

    fun getVendedores(
        baseUrl: String,
        user: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Vendedor>>> = flow<RequestState<List<Vendedor>>> {
        emit(RequestState.Loading)

        var reload = forceReload

        vendedorLocalDataSource.getVendedores(empresa).collect { cachedList ->
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
                            result.data.forEach { vendedor ->
                                addVendedor(vendedor)
                            }
                        }
                        else -> emit(RequestState.Loading)
                    }
                }
                reload = false
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun addVendedor(vendedor: Vendedor) =
        vendedorLocalDataSource.addVendedor(vendedor = vendedor.toDatabase())
}
