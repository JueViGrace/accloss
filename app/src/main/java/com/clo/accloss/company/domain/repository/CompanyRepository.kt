package com.clo.accloss.company.domain.repository

import com.clo.accloss.company.data.source.CompanyDataSource
import com.clo.accloss.company.domain.model.Company
import com.clo.accloss.core.state.RequestState
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CompanyRepository {
    val companyDataSource: CompanyDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteCompany(
        code: String
    ): RequestState<Company>

    fun getCompanies(): Flow<RequestState<List<Company>>>

    fun getCompany(code: String): Flow<RequestState<Company>>

    suspend fun addCompany(company: Company)

    suspend fun deleteCompany(company: String)
}
