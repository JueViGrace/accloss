package com.clo.accloss.orderlines.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.orderlines.data.remote.model.OrderLinesResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class OrderLinesRemoteImpl(
    private val ktorClient: KtorClient
) : OrderLinesRemote {
    override suspend fun getSafeOrderLines(
        baseUrl: String,
        document: String
    ): ApiOperation<OrderLinesResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_datos_pedidos_aux.php?kti_ndoc=$document"
            )
            .body<OrderLinesResponse>()
    }
}
