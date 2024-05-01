package com.clo.accloss.empresa.data.remote.source

import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.empresa.data.remote.model.EmpresaResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class EmpresaRemoteDataSource(
    private val ktorClient: KtorClient
) {
    fun getSafeEmpresa(
        codigo: String
    ): Flow<ApiOperation<EmpresaResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client()
            .get("/webservice/c_validar_empresa.php?codigo=$codigo")
            .body<EmpresaResponse>()
    }
}
