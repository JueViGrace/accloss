package com.clo.accloss.statistic.di

import com.clo.accloss.statistic.data.local.StatisticLocal
import com.clo.accloss.statistic.data.local.StatisticsLocalImpl
import com.clo.accloss.statistic.data.remote.source.StatisticRemote
import com.clo.accloss.statistic.data.remote.source.StatisticRemoteImpl
import com.clo.accloss.statistic.data.repository.StatisticRepositoryImpl
import com.clo.accloss.statistic.data.source.StatisticDataSource
import com.clo.accloss.statistic.data.source.StatisticDataSourceImpl
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.domain.usecase.GetProfileStatistics
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val statisticModule = module {
    singleOf(::StatisticRemoteImpl) bind StatisticRemote::class

    singleOf(::StatisticsLocalImpl) bind StatisticLocal::class

    singleOf(::StatisticDataSourceImpl) bind StatisticDataSource::class

    singleOf(::StatisticRepositoryImpl) bind StatisticRepository::class

    singleOf(::GetProfileStatistics)
}
