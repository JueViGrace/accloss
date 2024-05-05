package com.clo.accloss.lineasfactura.data.remote.source

import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.lineasfactura.data.remote.model.LineasFacturaResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class LineasFacturaRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeLineasFactura(
        baseUrl: String,
        documento: String
    ): ApiOperation<LineasFacturaResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_datos_facturas_aux.php?documento=$documento"
            )
            .body<LineasFacturaResponse>()
    }
}