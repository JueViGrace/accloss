package com.clo.accloss.company.data.remote.source

import com.clo.accloss.company.data.remote.model.CompanyResponse
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CompanyRemoteImpl(
    private val ktorClient: KtorClient
) : CompanyRemote {
    override suspend fun getSafeCompany(
        code: String
    ): ApiOperation<CompanyResponse> = ktorClient.safeApiCall {
        ktorClient
            .client()
            .get("/webservice/c_validar_empresa.php?codigo=$code")
            .body<CompanyResponse>()
    }
}
