package com.clo.accloss.bills.data.remote.source

import com.clo.accloss.bills.data.remote.model.BillResponse
import com.clo.accloss.core.data.network.ApiOperation

interface BillRemote {
    suspend fun getSafeBills(
        baseUrl: String,
        user: String,
    ): ApiOperation<BillResponse>
}
