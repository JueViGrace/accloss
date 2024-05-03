package com.clo.accloss.products.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.products.data.remote.model.ProductResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Date

class ProductRemoteDataSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeProducts(
        baseUrl: String,
        lastSync: String = Date().toStringFormat()
    ): ApiOperation<ProductResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = "/webservice/articulos_V26.php?fecha_sinc=${lastSync.replace(' ', '&')}"
            )
            .body<ProductResponse>()
    }
}
