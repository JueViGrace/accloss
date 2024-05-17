package com.clo.accloss.order.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.order.data.remote.model.OrderResponse
import java.util.Date

interface OrderRemote {
    suspend fun getSafeOrders(
        baseUrl: String,
        user: String,
        syncDate: String = Date().toStringFormat()
    ): ApiOperation<OrderResponse>
}
