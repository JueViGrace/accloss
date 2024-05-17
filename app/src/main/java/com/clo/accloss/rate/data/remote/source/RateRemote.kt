package com.clo.accloss.rate.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.rate.data.remote.model.RateResponse

interface RateRemote {
    suspend fun getSafeRates(
        baseUrl: String
    ): ApiOperation<RateResponse>
}
