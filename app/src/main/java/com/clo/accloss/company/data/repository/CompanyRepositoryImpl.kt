package com.clo.accloss.company.data.repository

import com.clo.accloss.company.data.source.CompanyDataSource
import com.clo.accloss.company.domain.mappers.toDatabase
import com.clo.accloss.company.domain.mappers.toDomain
import com.clo.accloss.company.domain.model.Company
import com.clo.accloss.company.domain.repository.CompanyRepository
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.domain.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CompanyRepositoryImpl(
    override val companyDataSource: CompanyDataSource
) : CompanyRepository {
    override suspend fun getRemoteCompany(
        code: String
    ): RequestState<Company> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = companyDataSource.companyRemote
                    .getSafeCompany(code = code)
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    RequestState.Success(
                        data = apiOperation.data.toDomain()
                    )
                }
            }
        }
    }

    override fun getCompanies(): Flow<RequestState<List<Company>>> = flow {
        emit(RequestState.Loading)

        companyDataSource.companyLocal.getCompanies()
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

    override fun getCompany(code: String): Flow<RequestState<Company>> = flow {
        emit(RequestState.Loading)

        companyDataSource.companyLocal.getCompany(code = code)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { companyEntity ->
                emit(RequestState.Success(data = companyEntity.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addCompany(company: Company) =
        withContext(Dispatchers.IO) {
            companyDataSource.companyLocal.addCompany(company = company.toDatabase())
        }
}
