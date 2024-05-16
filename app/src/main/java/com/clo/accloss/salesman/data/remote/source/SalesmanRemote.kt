package com.clo.accloss.salesman.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.salesman.data.remote.model.SalesmanResponse

interface SalesmanRemote {
    suspend fun getSafeSalesman(
        baseUrl: String,
        user: String
    ): ApiOperation<List<SalesmanResponse>>

    suspend fun getSafeMasters(
        baseUrl: String,
        user: String
    ): ApiOperation<List<SalesmanResponse>>
}
