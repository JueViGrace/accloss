package com.clo.accloss.statistic.data.remote.source

import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.statistic.data.remote.model.StatisticResponse
import java.util.Date

interface StatisticRemote {
    suspend fun getSafeStatistic(
        baseUrl: String,
        user: String,
        lastSync: String = Date().toStringFormat()
    ): ApiOperation<List<StatisticResponse>>
}
