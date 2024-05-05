package com.clo.accloss.gerencia.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.gerencia.data.remote.model.GerenciaResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class GerenciaRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeGerencias(
        baseUrl: String,
        user: String
    ): ApiOperation<List<GerenciaResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = "/webservice/c_gerencia.php?cod_usuario=$user"
            )
            .body<List<GerenciaResponse>>()
    }
}
