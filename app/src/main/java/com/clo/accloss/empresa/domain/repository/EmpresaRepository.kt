package com.clo.accloss.empresa.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.empresa.data.local.EmpresaLocalDataSource
import com.clo.accloss.empresa.data.remote.source.EmpresaRemoteDataSource
import com.clo.accloss.empresa.domain.mappers.toDatabase
import com.clo.accloss.empresa.domain.mappers.toDomain
import com.clo.accloss.empresa.domain.model.Empresa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class EmpresaRepository(
    private val empresaRemoteDataSource: EmpresaRemoteDataSource,
    private val empresaLocalDataSource: EmpresaLocalDataSource
) {

    fun getRemoteEmpresa(codigo: String): Flow<RequestState<Empresa>> = empresaRemoteDataSource
        .getSafeEmpresa(codigo = codigo)
        .map { apiOperation ->
            when (apiOperation) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error.message ?: "Internal Server Error"
                    )
                }
                is ApiOperation.Loading -> RequestState.Loading
                is ApiOperation.Success -> RequestState.Success(
                    data = apiOperation.data.toDomain()
                )
            }
        }

    suspend fun getEmpresas(): Flow<RequestState<List<Empresa>>> = flow {
        emit(RequestState.Loading)

        empresaLocalDataSource.getEmpresas()
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

        empresaLocalDataSource.getEmpresa(codigo = codigo)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { empresaEntity ->
                emit(RequestState.Success(data = empresaEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun addEmpresa(empresa: Empresa) = empresaLocalDataSource.addEmpresa(empresa = empresa.toDatabase())
}
