package com.clo.accloss.rate.di

import com.clo.accloss.rate.data.remote.source.RateRemoteSource
import com.clo.accloss.rate.domain.repository.RateRepository
import com.clo.accloss.rate.domain.usecase.GetRate
import org.koin.dsl.module

val rateModule = module {
    single {
        RateRemoteSource(get())
    }
    single {
        RateRepository(get())
    }
    single {
        GetRate(get(), get())
    }
}
