package com.clo.accloss.salesman.data.remote.source

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.salesman.data.remote.model.SalesmanResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

class SalesmanRemoteImpl(
    private val ktorClient: KtorClient
) : SalesmanRemote {
    override suspend fun getSafeSalesman(
        baseUrl: String,
        user: String
    ): ApiOperation<List<SalesmanResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_vendedor.php?codigo=$user"
            )
            .body<List<SalesmanResponse>>()
    }

    override suspend fun getSafeMasters(
        baseUrl: String,
        user: String
    ): ApiOperation<List<SalesmanResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_datos_maestros.php?codigo=$user"
            )
            .body<List<SalesmanResponse>>()
    }
}
