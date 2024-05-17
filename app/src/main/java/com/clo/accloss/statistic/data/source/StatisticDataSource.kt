package com.clo.accloss.statistic.data.source

import com.clo.accloss.statistic.data.local.StatisticLocal
import com.clo.accloss.statistic.data.remote.source.StatisticRemote

interface StatisticDataSource {
    val statisticRemote: StatisticRemote
    val statisticLocal: StatisticLocal
}
