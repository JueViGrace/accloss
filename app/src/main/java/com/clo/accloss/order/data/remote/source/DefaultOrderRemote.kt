package com.clo.accloss.order.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.order.data.remote.model.OrderResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultOrderRemote(
    private val ktorClient: KtorClient
) : OrderRemote {
    override suspend fun getSafeOrders(
        baseUrl: String,
        user: String,
        syncDate: String
    ): ApiOperation<OrderResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_pedidos.php?cod_usuario=$user&fecha_sinc=$syncDate"
            )
            .body<OrderResponse>()
    }
}
