package com.clo.accloss.order.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.order.data.remote.model.OrderResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Date

class OrderRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeOrders(
        baseUrl: String,
        user: String,
        syncDate: String = Date().toStringFormat()
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