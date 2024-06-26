package com.clo.accloss.bills.data.remote.source

import com.clo.accloss.bills.data.remote.model.BillResponse
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultBillRemote(
    private val ktorClient: KtorClient
) : BillRemote {
    override suspend fun getSafeBills(
        baseUrl: String,
        user: String,
    ): ApiOperation<BillResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_facturas.php?cod_usuario=$user"
            )
            .body<BillResponse>()
    }
}
