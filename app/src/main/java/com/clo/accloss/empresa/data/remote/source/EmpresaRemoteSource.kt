package com.clo.accloss.empresa.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.empresa.data.remote.model.EmpresaResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class EmpresaRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeEmpresa(
        codigo: String
    ): ApiOperation<EmpresaResponse> = ktorClient.safeApiCall {
        ktorClient
            .client()
            .get("/webservice/c_validar_empresa.php?codigo=$codigo")
            .body<EmpresaResponse>()
    }
}
