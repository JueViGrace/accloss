package com.clo.accloss.cliente.data.remote.source

import com.clo.accloss.cliente.data.remote.model.ClienteResponse
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ClienteRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeClientes(
        baseUrl: String,
        user: String
    ): ApiOperation<ClienteResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = "/webservice/c_clientes.php?cod_usuario=$user"
            )
            .body<ClienteResponse>()
    }
}
