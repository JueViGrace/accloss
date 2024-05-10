package com.clo.accloss.company.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.company.data.local.CompanyLocalSource
import com.clo.accloss.company.data.remote.source.CompanyRemoteSource
import com.clo.accloss.company.domain.mappers.toDatabase
import com.clo.accloss.company.domain.mappers.toDomain
import com.clo.accloss.company.domain.model.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CompanyRepository(
    private val companyRemoteSource: CompanyRemoteSource,
    private val companyLocalSource: CompanyLocalSource
) {
    fun getRemoteCompany(
        code: String
    ): Flow<RequestState<Company>> = flow {
        emit(RequestState.Loading)

        val apiOperation = companyRemoteSource
            .getSafeCompany(code = code)

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

    suspend fun getCompanies(): Flow<RequestState<List<Company>>> = flow {
        emit(RequestState.Loading)

        companyLocalSource.getCompanies()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { list ->
                if (list.isNotEmpty()) {
                    emit(
                        RequestState.Success(
                            data = list.map { companyEntity ->
                                companyEntity.toDomain()
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

    suspend fun getCompany(code: String): Flow<RequestState<Company>> = flow {
        emit(RequestState.Loading)

        companyLocalSource.getEmpresa(code = code)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { companyEntity ->
                emit(RequestState.Success(data = companyEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun addCompany(company: Company) = companyLocalSource.addCompany(company = company.toDatabase())
}
