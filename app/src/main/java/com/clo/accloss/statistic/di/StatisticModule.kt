package com.clo.accloss.statistic.di

import com.clo.accloss.statistic.data.local.StatisticsLocalSource
import com.clo.accloss.statistic.data.remote.source.StatisticRemoteSource
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.domain.usecase.GetProfileStatistics
import org.koin.dsl.module

val statisticModule = module {
    single {
        StatisticsLocalSource(get(), get())
    }
    single {
        StatisticRemoteSource(get())
    }
    single {
        StatisticRepository(get(), get())
    }
    single {
        GetProfileStatistics(get(), get())
    }
}
