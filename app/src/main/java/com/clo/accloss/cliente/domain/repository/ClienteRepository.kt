package com.clo.accloss.cliente.domain.repository

import com.clo.accloss.cliente.data.local.ClienteLocalSource
import com.clo.accloss.cliente.data.remote.source.ClienteRemoteSource
import com.clo.accloss.cliente.domain.mappers.toDatabase
import com.clo.accloss.cliente.domain.mappers.toDomain
import com.clo.accloss.cliente.domain.model.Cliente
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ClienteRepository(
    private val clienteRemoteSource: ClienteRemoteSource,
    private val clienteLocalSource: ClienteLocalSource
) {
    private fun getRemoteClientes(
        baseUrl: String,
        empresa: String,
        user: String
    ): Flow<RequestState<List<Cliente>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = clienteRemoteSource
            .getSafeClientes(
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
                        data = apiOperation.data.clientes.map { clieteItem ->
                            clieteItem.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getClientes(
        empresa: String,
        baseUrl: String,
        user: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Cliente>>> = flow<RequestState<List<Cliente>>> {
        emit(RequestState.Loading)

        var reload = forceReload

        clienteLocalSource.getClientes(empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { clienteEntity ->
                                clienteEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteClientes(
                        baseUrl = baseUrl,
                        empresa = empresa,
                        user = user
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
                                result.data.forEach { cliente ->
                                    addCliente(cliente)
                                }
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getCliente(
        codigo: String,
        empresa: String
    ): Flow<RequestState<Cliente>> = flow<RequestState<Cliente>> {
        emit(RequestState.Loading)

        clienteLocalSource.getCliente(
            codigo = codigo,
            empresa = empresa
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { clienteEntity ->
                emit(
                    RequestState.Success(
                        data = clienteEntity.toDomain()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addCliente(cliente: Cliente) =
        clienteLocalSource.addCliente(cliente.toDatabase())
}
