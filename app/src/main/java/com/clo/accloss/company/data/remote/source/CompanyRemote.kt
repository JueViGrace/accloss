package com.clo.accloss.company.data.remote.source

import com.clo.accloss.company.data.remote.model.CompanyResponse
import com.clo.accloss.core.data.network.ApiOperation

interface CompanyRemote {
    suspend fun getSafeCompany(
        code: String
    ): ApiOperation<CompanyResponse>
}
