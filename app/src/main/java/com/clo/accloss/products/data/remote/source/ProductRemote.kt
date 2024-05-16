package com.clo.accloss.products.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.products.data.remote.model.ProductResponse
import java.util.Date

interface ProductRemote {
    suspend fun getSafeProducts(
        baseUrl: String,
        lastSync: String = Date().toStringFormat()
    ): ApiOperation<ProductResponse>
}
