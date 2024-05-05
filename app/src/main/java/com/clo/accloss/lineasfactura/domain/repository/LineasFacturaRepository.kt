package com.clo.accloss.lineasfactura.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.factura.domain.model.Factura
import com.clo.accloss.lineasfactura.data.local.LineasFacturaLocalSource
import com.clo.accloss.lineasfactura.data.remote.source.LineasFacturaRemoteSource
import com.clo.accloss.lineasfactura.domain.mappers.toDatabase
import com.clo.accloss.lineasfactura.domain.mappers.toDomain
import com.clo.accloss.lineasfactura.domain.model.LineasFactura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LineasFacturaRepository(
    private val lineasFacturaRemoteSource: LineasFacturaRemoteSource,
    private val lineasFacturaLocalSource: LineasFacturaLocalSource
) {
    private fun getRemoteLineasFactura(
        baseUrl: String,
        documento: String,
        empresa: String
    ): Flow<RequestState<List<LineasFactura>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = lineasFacturaRemoteSource
            .getSafeLineasFactura(
                baseUrl = baseUrl,
                documento = documento
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
                        data = apiOperation.data.lineasFacturas.map { lineasFacturasItem ->
                            lineasFacturasItem.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getLineasFactura(
        baseUrl: String,
        documento: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<LineasFactura>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        lineasFacturaLocalSource.getLineasFactura(
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
                            data = cachedList.map { lineasFacturaEntity ->
                                lineasFacturaEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteLineasFactura(
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
                                    result.data.forEach { lineasFactura ->
                                        addLineasFactura(lineasFactura)
                                    }
                                }
                                else -> emit(RequestState.Loading)
                            }
                        }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addLineasFactura(lineasFactura: LineasFactura) =
        lineasFacturaLocalSource.addLineasFactura(lineasFactura = lineasFactura.toDatabase())
}
