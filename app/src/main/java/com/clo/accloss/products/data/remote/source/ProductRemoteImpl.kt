package com.clo.accloss.products.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.products.data.remote.model.ProductResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductRemoteImpl(
    private val ktorClient: KtorClient
) : ProductRemote {
    override suspend fun getSafeProducts(
        baseUrl: String,
        lastSync: String
    ): ApiOperation<ProductResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = "/webservice/articulos_V26.php?fecha_sinc=${lastSync.replace(' ', '&')}"
            )
            .body<ProductResponse>()
    }
}
