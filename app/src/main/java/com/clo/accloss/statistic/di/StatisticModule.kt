package com.clo.accloss.statistic.di

import com.clo.accloss.statistic.data.local.StatisticLocal
import com.clo.accloss.statistic.data.local.DefaultStatisticsLocal
import com.clo.accloss.statistic.data.remote.source.StatisticRemote
import com.clo.accloss.statistic.data.remote.source.DefaultStatisticRemote
import com.clo.accloss.statistic.data.repository.DefaultStatisticRepository
import com.clo.accloss.statistic.data.source.StatisticDataSource
import com.clo.accloss.statistic.data.source.StatisticDataSourceImpl
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.statistic.domain.usecase.GetManagementStatistics
import com.clo.accloss.statistic.domain.usecase.GetPersonalStatistics
import com.clo.accloss.statistic.domain.usecase.GetProfileStatistics
import com.clo.accloss.statistic.domain.usecase.GetSalesmenStatistics
import com.clo.accloss.statistic.presentation.viewmodel.StatisticDetailsViewModel
import com.clo.accloss.statistic.presentation.viewmodel.StatisticsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val statisticModule = module {
    singleOf(::DefaultStatisticRemote) bind StatisticRemote::class

    singleOf(::DefaultStatisticsLocal) bind StatisticLocal::class

    singleOf(::StatisticDataSourceImpl) bind StatisticDataSource::class

    singleOf(::DefaultStatisticRepository) bind StatisticRepository::class

    singleOf(::GetProfileStatistics)

    singleOf(::GetSalesmenStatistics)

    singleOf(::GetPersonalStatistics)

    singleOf(::GetManagementStatistics)

    factoryOf(::StatisticsViewModel)

    factoryOf(::StatisticDetailsViewModel)
}
