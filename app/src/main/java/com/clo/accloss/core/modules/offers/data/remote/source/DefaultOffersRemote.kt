package com.clo.accloss.core.modules.offers.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.core.modules.offers.data.remote.model.ImageResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultOffersRemote(
    private val ktorClient: KtorClient,
    override val coroutineContext: CoroutineContext
) : OffersRemote {
    override suspend fun getImages(
        baseUrl: String,
    ): ApiOperation<ImageResponse> {
        return withContext(coroutineContext) {
            ktorClient.safeApiCall {
                ktorClient
                    .client(
                        baseUrl = baseUrl
                    )
                    .get(
                        urlString = "/webservice/promociones.php?"
                    )
                    .body<ImageResponse>()
            }
        }
    }
}
