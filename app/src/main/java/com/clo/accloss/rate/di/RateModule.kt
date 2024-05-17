package com.clo.accloss.rate.di

import com.clo.accloss.rate.data.local.RateLocal
import com.clo.accloss.rate.data.local.RateLocalImpl
import com.clo.accloss.rate.data.remote.source.RateRemote
import com.clo.accloss.rate.data.remote.source.RateRemoteImpl
import com.clo.accloss.rate.data.repository.RateRepositoryImpl
import com.clo.accloss.rate.data.source.RateDataSource
import com.clo.accloss.rate.data.source.RateDataSourceImpl
import com.clo.accloss.rate.domain.repository.RateRepository
import com.clo.accloss.rate.domain.usecase.GetRate
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val rateModule = module {
    singleOf(::RateRemoteImpl) bind RateRemote::class

    singleOf(::RateLocalImpl) bind RateLocal::class

    singleOf(::RateDataSourceImpl) bind RateDataSource::class

    singleOf(::RateRepositoryImpl) bind RateRepository::class

    singleOf(::GetRate)
}
