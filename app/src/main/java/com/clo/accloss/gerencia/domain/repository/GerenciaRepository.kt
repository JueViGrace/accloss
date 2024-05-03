package com.clo.accloss.gerencia.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.gerencia.data.local.GerenciaLocalDataSource
import com.clo.accloss.gerencia.data.remote.source.GerenciaRemoteDataSource
import com.clo.accloss.gerencia.domain.mappers.toDatabase
import com.clo.accloss.gerencia.domain.mappers.toDomain
import com.clo.accloss.gerencia.domain.model.Gerencia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GerenciaRepository(
    private val gerenciaRemoteDataSource: GerenciaRemoteDataSource,
    private val gerenciaLocalDataSource: GerenciaLocalDataSource
) {
    fun getRemoteGerencia(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Gerencia>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = gerenciaRemoteDataSource
            .getSafeGerencias(
                baseUrl = baseUrl,
                user = user
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
                val list = apiOperation.data.map { gerenciaResponse ->
                    gerenciaResponse.toDomain().copy(empresa = empresa)
                }

                list.forEach { gerencia ->
                    addGerencia(gerencia)
                }

                emit(
                    RequestState.Success(
                        data = list
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun addGerencia(gerencia: Gerencia) =
        gerenciaLocalDataSource.addGerencias(gerencia = gerencia.toDatabase())
}
