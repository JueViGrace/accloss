package com.clo.accloss.lineaspedido.data.remote.source

import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.lineaspedido.data.remote.model.LineasPedidoResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class LineasPedidoRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeLineasPedido(
        baseUrl: String,
        documento: String
    ): ApiOperation<LineasPedidoResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_datos_pedidos_aux.php?kti_ndoc=$documento"
            )
            .body<LineasPedidoResponse>()
    }
}