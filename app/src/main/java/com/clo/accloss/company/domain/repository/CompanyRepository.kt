package com.clo.accloss.company.domain.repository

import com.clo.accloss.company.data.source.CompanyDataSource
import com.clo.accloss.company.domain.model.Company
import com.clo.accloss.core.presentation.state.RequestState
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    val companyDataSource: CompanyDataSource

    suspend fun getRemoteCompany(
        code: String
    ): RequestState<Company>

    fun getCompanies(): Flow<RequestState<List<Company>>>

    fun getCompany(code: String): Flow<RequestState<Company>>

    suspend fun addCompany(company: Company)
}
