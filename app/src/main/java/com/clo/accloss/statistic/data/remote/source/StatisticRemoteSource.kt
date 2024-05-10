package com.clo.accloss.statistic.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.data.network.KtorClient
import com.clo.accloss.statistic.data.remote.model.StatisticResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.util.Date

class StatisticRemoteSource(
    private val ktorClient: KtorClient
) {
    suspend fun getSafeStatistic(
        baseUrl: String,
        user: String,
        lastSync: String = Date().toStringFormat()
    ): ApiOperation<List<StatisticResponse>> = ktorClient.safeApiCall {
        ktorClient
            .client(
                baseUrl = baseUrl
            )
            .get(
                urlString = "/webservice/c_estadisticas.php?cod_usuario=$user&fecha_sinc=${lastSync.replace(' ', '&')}"
            )
            .body<List<StatisticResponse>>()
    }
}
