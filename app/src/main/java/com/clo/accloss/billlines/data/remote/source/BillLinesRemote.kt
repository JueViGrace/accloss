package com.clo.accloss.billlines.data.remote.source

import com.clo.accloss.billlines.data.remote.model.BillLinesResponse
import com.clo.accloss.core.data.network.ApiOperation

interface BillLinesRemote {
    suspend fun getSafeBillLines(
        baseUrl: String,
        document: String
    ): ApiOperation<BillLinesResponse>
}
