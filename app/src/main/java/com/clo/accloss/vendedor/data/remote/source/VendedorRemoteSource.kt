package com.clo.accloss.vendedor.data.remote.source

import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.vendedor.data.remote.model.VendedorResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class VendedorRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeVendedor(
        baseUrl: String,
        user: String
    ): ApiOperation<List<VendedorResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_vendedor.php?codigo=$user"
            )
            .body<List<VendedorResponse>>()
    }
}
