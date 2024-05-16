package com.clo.accloss.customer.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.customer.data.remote.model.CustomerResponse

interface CustomerRemote {
    suspend fun getSafeCustomers(
        baseUrl: String,
        user: String
    ): ApiOperation<CustomerResponse>
}
