package com.clo.accloss.vendedor.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
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

    suspend fun getSafeCoordinaciones(
        baseUrl: String,
        user: String
    ): ApiOperation<List<VendedorResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_datos_maestros.php?codigo=$user"
            )
            .body<List<VendedorResponse>>()
    }
}
