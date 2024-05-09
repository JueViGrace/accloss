package com.clo.accloss.lineaspedido.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.lineasfactura.domain.mappers.toDatabase
import com.clo.accloss.lineasfactura.domain.mappers.toDomain
import com.clo.accloss.lineaspedido.data.local.LineasPedidoLocalSource
import com.clo.accloss.lineaspedido.data.remote.source.LineasPedidoRemoteSource
import com.clo.accloss.lineaspedido.domain.mappers.toDatabase
import com.clo.accloss.lineaspedido.domain.mappers.toDomain
import com.clo.accloss.lineaspedido.domain.model.LineasPedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LineasPedidoRepository(
    private val lineasPedidoRemoteSource: LineasPedidoRemoteSource,
    private val lineasPedidoLocalSource: LineasPedidoLocalSource
) {
    fun getRemoteLineasPedido(
        baseUrl: String,
        documento: String,
        empresa: String
    ): Flow<RequestState<List<LineasPedido>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = lineasPedidoRemoteSource
            .getSafeLineasPedido(
                baseUrl = baseUrl,
                documento = documento
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error.message ?: Constants.SERVER_ERROR
                    )
                )
            }
            is ApiOperation.Success -> {
                emit(
                    RequestState.Success(
                        data = apiOperation.data.lineasPedidos.map { lineasPedidoItem ->
                            lineasPedidoItem.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getLineasPedido(
        baseUrl: String,
        documento: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<LineasPedido>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        lineasPedidoLocalSource.getLineasPedido(
            documento = documento,
            empresa = empresa
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { lineasPedidoEntity ->
                                lineasPedidoEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteLineasPedido(
                        baseUrl = baseUrl,
                        documento = documento,
                        empresa = empresa
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
                                    addLineasPedido(result.data)
                                }
                                else -> emit(RequestState.Loading)
                            }
                        }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addLineasPedido(lineasPedido: List<LineasPedido>) =
        lineasPedidoLocalSource.addLineasPedido(
            lineasPedido = lineasPedido.map { linea ->
                linea.toDatabase()
            }
        )
}
