package com.clo.accloss.estadistica.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.estadistica.data.local.EstadisticaLocalSource
import com.clo.accloss.estadistica.data.remote.source.EstadisticaRemoteSource
import com.clo.accloss.estadistica.domain.mappers.toDatabase
import com.clo.accloss.estadistica.domain.mappers.toDomain
import com.clo.accloss.estadistica.domain.model.Estadistica
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EstadisticaRepository(
    private val estadisticaRemoteSource: EstadisticaRemoteSource,
    private val estadisticaLocalSource: EstadisticaLocalSource
) {
    private fun getRemoteEstadistica(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Estadistica>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = estadisticaRemoteSource
            .getSafeEstadistica(
                baseUrl = baseUrl,
                user = user
            )

        when(apiOperation) {
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
                        data = apiOperation.data.map { estadisticaResponse ->
                            estadisticaResponse.toDomain().copy(empresa = empresa)
                        }
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getEstadisticas(
        baseUrl: String,
        user: String,
        empresa: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Estadistica>>> = flow {
        emit(RequestState.Loading)

        var reload = forceReload

        estadisticaLocalSource.getEstadisiticas(empresa)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { estadisticaEntity ->
                                estadisticaEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteEstadistica(
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
                                result.data.forEach { estadistica ->
                                    addEstadistica(estadistica)
                                }
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addEstadistica(estadistica: Estadistica) =
        estadisticaLocalSource.addEstadistica(estadistica = estadistica.toDatabase())
}
