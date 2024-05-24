package com.clo.accloss.configuration.data.remote.source

import com.clo.accloss.configuration.data.remote.model.ConfigurationResponse
import com.clo.accloss.core.data.network.ApiOperation

interface ConfigurationRemote {
    suspend fun getRemoteConfiguration(
        baseUrl: String,
        salesman: String
    ): ApiOperation<ConfigurationResponse>
}
