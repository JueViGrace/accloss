package com.clo.accloss.billlines.data.remote.source

import com.clo.accloss.billlines.data.remote.model.BillLinesResponse
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BillLinesRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeBillLines(
        baseUrl: String,
        document: String
    ): ApiOperation<BillLinesResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_datos_facturas_aux.php?documento=$document"
            )
            .body<BillLinesResponse>()
    }
}
