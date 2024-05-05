package com.clo.accloss.empresa.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.empresa.data.local.EmpresaLocalSource
import com.clo.accloss.empresa.data.remote.source.EmpresaRemoteSource
import com.clo.accloss.empresa.domain.mappers.toDatabase
import com.clo.accloss.empresa.domain.mappers.toDomain
import com.clo.accloss.empresa.domain.model.Empresa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EmpresaRepository(
    private val empresaRemoteSource: EmpresaRemoteSource,
    private val empresaLocalSource: EmpresaLocalSource
) {
    fun getRemoteEmpresa(
        codigo: String
    ): Flow<RequestState<Empresa>> = flow {
        emit(RequestState.Loading)

        val apiOperation = empresaRemoteSource
            .getSafeEmpresa(codigo = codigo)

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
                        data = apiOperation.data.toDomain()
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getEmpresas(): Flow<RequestState<List<Empresa>>> = flow {
        emit(RequestState.Loading)

        empresaLocalSource.getEmpresas()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { list ->
                if (list.isNotEmpty()) {
                    emit(
                        RequestState.Success(
                            data = list.map { empresaEntity ->
                                empresaEntity.toDomain()
                            }
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = "No empresas"
                        )
                    )
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getEmpresa(codigo: String): Flow<RequestState<Empresa>> = flow {
        emit(RequestState.Loading)

        empresaLocalSource.getEmpresa(codigo = codigo)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { empresaEntity ->
                emit(RequestState.Success(data = empresaEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun addEmpresa(empresa: Empresa) = empresaLocalSource.addEmpresa(empresa = empresa.toDatabase())
}
