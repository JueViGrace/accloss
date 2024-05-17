package com.clo.accloss.orderlines.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.orderlines.data.remote.model.OrderLinesResponse

interface OrderLinesRemote {
    suspend fun getSafeOrderLines(
        baseUrl: String,
        document: String
    ): ApiOperation<OrderLinesResponse>
}
