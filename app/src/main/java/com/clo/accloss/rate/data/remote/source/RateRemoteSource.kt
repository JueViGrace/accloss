package com.clo.accloss.rate.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.rate.data.remote.model.RateResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class RateRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeRates(
        baseUrl: String
    ): ApiOperation<RateResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_tasas.php?"
            )
            .body<RateResponse>()
    }
}
