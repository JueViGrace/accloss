package com.clo.accloss.management.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.management.data.remote.model.ManagementResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class ManagementRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeManagements(
        baseUrl: String,
        user: String
    ): ApiOperation<List<ManagementResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = "/webservice/c_gerencia.php?cod_usuario=$user"
            )
            .body<List<ManagementResponse>>()
    }
}
