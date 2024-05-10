package com.clo.accloss.customer.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.customer.data.remote.model.CustomerResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class CustomerRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeCustomers(
        baseUrl: String,
        user: String
    ): ApiOperation<CustomerResponse> = ktorClient.safeApiCall {
        ktorClient
            .client(baseUrl = baseUrl)
            .get(
                urlString = "/webservice/c_clientes.php?cod_usuario=$user"
            )
            .body<CustomerResponse>()
    }
}
