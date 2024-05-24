package com.clo.accloss.configuration.data.remote.source

import com.clo.accloss.configuration.data.remote.model.ConfigurationResponse
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DefaultConfigurationRemote(
    private val ktorClient: KtorClient
) : ConfigurationRemote {
    override suspend fun getRemoteConfiguration(
        baseUrl: String,
        salesman: String
    ): ApiOperation<ConfigurationResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/config_gen2.php?vendedor=$salesman"
            )
            .body<ConfigurationResponse>()
    }
}
