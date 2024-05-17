package com.clo.accloss.management.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.management.data.remote.model.ManagementResponse

interface ManagementRemote {
    suspend fun getSafeManagements(
        baseUrl: String,
        user: String
    ): ApiOperation<List<ManagementResponse>>
}
