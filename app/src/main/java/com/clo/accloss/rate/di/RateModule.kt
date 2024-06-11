package com.clo.accloss.rate.di

import com.clo.accloss.rate.data.local.RateLocal
import com.clo.accloss.rate.data.local.DefaultRateLocal
import com.clo.accloss.rate.data.remote.source.RateRemote
import com.clo.accloss.rate.data.remote.source.DefaultRateRemote
import com.clo.accloss.rate.data.repository.DefaultRateRepository
import com.clo.accloss.rate.data.source.RateDataSource
import com.clo.accloss.rate.data.source.RateDataSourceImpl
import com.clo.accloss.rate.domain.repository.RateRepository
import com.clo.accloss.rate.domain.usecase.GetRate
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val rateModule = module {
    singleOf(::DefaultRateRemote) bind RateRemote::class

    singleOf(::DefaultRateLocal) bind RateLocal::class

    singleOf(::RateDataSourceImpl) bind RateDataSource::class

    singleOf(::DefaultRateRepository) bind RateRepository::class

    singleOf(::GetRate)
}
