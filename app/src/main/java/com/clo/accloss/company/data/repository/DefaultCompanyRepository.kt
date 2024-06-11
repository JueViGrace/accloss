package com.clo.accloss.company.data.repository

import com.clo.accloss.R
import com.clo.accloss.company.data.source.CompanyDataSource
import com.clo.accloss.company.domain.mappers.toDatabase
import com.clo.accloss.company.domain.mappers.toDomain
import com.clo.accloss.company.domain.model.Company
import com.clo.accloss.company.domain.repository.CompanyRepository
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultCompanyRepository(
    override val companyDataSource: CompanyDataSource,
    override val coroutineContext: CoroutineContext
) : CompanyRepository {
    override suspend fun getRemoteCompany(
        code: String
    ): RequestState<Company> {
        return withContext(coroutineContext) {
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

        companyDataSource.companyLocal
            .getCompanies()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("COMPANY REPOSITORY: getCompanies")
            }
            .collect { cachedList ->
                emit(
                    RequestState.Success(
                        data = cachedList.map { companyEntity ->
                            companyEntity.toDomain()
                        }
                    )
                )
            }
    }.flowOn(coroutineContext)

    override fun getCompany(code: String): Flow<RequestState<Company>> = flow {
        emit(RequestState.Loading)

        companyDataSource.companyLocal.getCompany(code = code)
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("COMPANY REPOSITORY: getCompany")
            }
            .collect { companyEntity ->
                if (companyEntity != null) {
                    emit(
                        RequestState.Success(data = companyEntity.toDomain())
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.this_company_doesn_t_exists
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override suspend fun addCompany(company: Company) {
        withContext(coroutineContext) {
            companyDataSource.companyLocal
                .addCompany(
                    company = company.toDatabase()
                )
        }
    }

    override suspend fun deleteCompany(company: String) {
        withContext(coroutineContext) {
            companyDataSource.companyLocal
                .deleteCompany(
                    company = company
                )
        }
    }
}
