package com.clo.accloss.factura.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.factura.data.local.FacturaLocalSource
import com.clo.accloss.factura.data.remote.source.FacturaRemoteSource
import com.clo.accloss.factura.domain.mappers.toDatabase
import com.clo.accloss.factura.domain.mappers.toDomain
import com.clo.accloss.factura.domain.model.Factura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FacturaRepository(
    private val facturaRemoteSource: FacturaRemoteSource,
    private val facturaLocalSource: FacturaLocalSource
) {
    private fun getRemoteFactura(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Factura>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = facturaRemoteSource
            .getSafeFacturas(
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
                        data = apiOperation.data.facturas.map { facturaResponse ->
                            facturaResponse.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getFacturas(
        baseUrl: String,
        user: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Factura>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        facturaLocalSource.getFacturas(empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: Constants.DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { facturaEntity ->
                                facturaEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteFactura(
                        baseUrl = baseUrl,
                        user = user,
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
                                    result.data.forEach { factura: Factura ->
                                        addFactura(factura)
                                    }
                                }
                                else -> emit(RequestState.Loading)
                            }
                        }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addFactura(factura: Factura) =
        facturaLocalSource.addFactura(factura = factura.toDatabase())
}
