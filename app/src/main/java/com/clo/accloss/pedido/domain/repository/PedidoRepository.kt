package com.clo.accloss.pedido.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.pedido.data.local.PedidoLocalSource
import com.clo.accloss.pedido.data.remote.source.PedidoRemoteSource
import com.clo.accloss.pedido.domain.mappers.toDatabase
import com.clo.accloss.pedido.domain.mappers.toDomain
import com.clo.accloss.pedido.domain.model.Pedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PedidoRepository(
    private val pedidoRemoteSource: PedidoRemoteSource,
    private val pedidoLocalSource: PedidoLocalSource
) {
    private fun getRemotePedidos(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Pedido>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = pedidoRemoteSource
            .getSafePedidos(
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
                        data = apiOperation.data.pedidos.map { pedidoResponse ->
                            pedidoResponse.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getPedidos(
        baseUrl: String,
        user: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Pedido>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        pedidoLocalSource.getPedidos(empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { pedidoEntity ->
                                pedidoEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemotePedidos(
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
                                result.data.forEach { pedido ->
                                    addPedido(pedido)
                                }
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    fun getPedido(
        pedido: String,
        empresa: String
    ): Flow<RequestState<Pedido>> = flow<RequestState<Pedido>> {
        emit(RequestState.Loading)

        pedidoLocalSource.getPedido(
            pedido = pedido,
            empresa = empresa
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { pedidoEntity ->
                emit(
                    RequestState.Success(
                        data = pedidoEntity.toDomain()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addPedido(pedido: Pedido) =
        pedidoLocalSource.addPedido(pedido = pedido.toDatabase())
}
