package com.clo.accloss.pedido.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.pedido.data.remote.model.PedidoResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Date

class PedidoRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafePedidos(
        baseUrl: String,
        user: String,
        fechaSinc: String = Date().toStringFormat()
    ): ApiOperation<PedidoResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_pedidos.php?cod_usuario=$user&fecha_sinc=$fechaSinc"
            )
            .body<PedidoResponse>()
    }
}