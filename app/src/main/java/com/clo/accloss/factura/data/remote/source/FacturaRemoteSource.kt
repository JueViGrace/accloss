package com.clo.accloss.factura.data.remote.source

import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.factura.data.remote.model.FacturaResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class FacturaRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeFacturas(
        baseUrl: String,
        user: String,
    ): ApiOperation<FacturaResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_facturas.php?cod_usuario=$user"
            )
            .body<FacturaResponse>()
    }
}