package com.clo.accloss.gerencia.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.gerencia.data.local.GerenciaLocalSource
import com.clo.accloss.gerencia.data.remote.source.GerenciaRemoteSource
import com.clo.accloss.gerencia.domain.mappers.toDatabase
import com.clo.accloss.gerencia.domain.mappers.toDomain
import com.clo.accloss.gerencia.domain.model.Gerencia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GerenciaRepository(
    private val gerenciaRemoteSource: GerenciaRemoteSource,
    private val gerenciaLocalSource: GerenciaLocalSource
) {
    fun getRemoteGerencia(
        baseUrl: String,
        user: String,
        empresa: String
    ): Flow<RequestState<List<Gerencia>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = gerenciaRemoteSource
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
                addGerencia(list)
                emit(
                    RequestState.Success(
                        data = list
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun addGerencia(gerencias: List<Gerencia>) =
        gerenciaLocalSource.addGerencias(
            gerencias = gerencias.map { gerencia ->
                gerencia.toDatabase()
            }
        )
}
