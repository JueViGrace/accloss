package com.clo.accloss.bills.di

import com.clo.accloss.bills.data.local.BillLocalSource
import com.clo.accloss.bills.data.remote.source.BillRemoteSource
import com.clo.accloss.bills.domain.repository.BillRepository
import org.koin.dsl.module

val billModule = module {
    single {
        BillLocalSource(get(), get())
    }
    single {
        BillRemoteSource(get())
    }
    single {
        BillRepository(get(), get())
    }
}
