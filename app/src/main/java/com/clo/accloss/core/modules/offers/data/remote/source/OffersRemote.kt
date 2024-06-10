package com.clo.accloss.core.modules.offers.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.modules.offers.data.remote.model.ImageResponse
import kotlin.coroutines.CoroutineContext

interface OffersRemote {
    val coroutineContext: CoroutineContext

    suspend fun getImages(
        baseUrl: String
    ): ApiOperation<ImageResponse>
}
