package com.clo.accloss.estadistica.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.network.ApiOperation
import com.clo.accloss.core.network.KtorClient
import com.clo.accloss.estadistica.data.remote.model.EstadisticaResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Date

class EstadisticaRemoteDataSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeEstadistica(
        baseUrl: String,
        user: String,
        fechaSinc: String = Date().toStringFormat()
    ): ApiOperation<List<EstadisticaResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_estadisticas.php?cod_usuario=$user&fecha_sinc=$fechaSinc"
            )
            .body<List<EstadisticaResponse>>()
    }
}
