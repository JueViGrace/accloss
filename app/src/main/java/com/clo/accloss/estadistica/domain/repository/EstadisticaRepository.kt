package com.clo.accloss.estadistica.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.estadistica.data.local.EstadisticaLocalDataSource
import com.clo.accloss.estadistica.data.remote.source.EstadisticaRemoteDataSource
import com.clo.accloss.estadistica.domain.mappers.toDatabase
import com.clo.accloss.estadistica.domain.mappers.toDomain
import com.clo.accloss.estadistica.domain.model.Estadistica
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EstadisticaRepository(
    private val estadisticaRemoteDataSource: EstadisticaRemoteDataSource,
    private val estadisticaLocalDataSource: EstadisticaLocalDataSource
) {
    private fun getRemoteEstadistica(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Estadistica>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = estadisticaRemoteDataSource
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

        estadisticaLocalDataSource.getEstadisiticas(empresa)
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
        estadisticaLocalDataSource.addEstadistica(estadistica = estadistica.toDatabase())
}
