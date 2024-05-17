package com.clo.accloss.statistic.data.source

import com.clo.accloss.statistic.data.local.StatisticLocal
import com.clo.accloss.statistic.data.remote.source.StatisticRemote

class StatisticDataSourceImpl(
    override val statisticRemote: StatisticRemote,
    override val statisticLocal: StatisticLocal
) : StatisticDataSource
