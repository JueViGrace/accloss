package com.clo.accloss.company.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Empresa as CompanyEntity

interface CompanyLocal {
    suspend fun getCompanies(): Flow<List<CompanyEntity>>

    suspend fun getCompany(code: String): Flow<CompanyEntity>

    suspend fun addCompany(company: CompanyEntity)
}
