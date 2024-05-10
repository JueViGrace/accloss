package com.clo.accloss.customer.di

import com.clo.accloss.customer.data.local.CustomerLocalSource
import com.clo.accloss.customer.data.remote.source.CustomerRemoteSource
import com.clo.accloss.customer.domain.repository.CustomerRepository
import org.koin.dsl.module

val customerModule = module {
    single {
        CustomerLocalSource(get(), get())
    }
    single {
        CustomerRemoteSource(get())
    }
    single {
        CustomerRepository(get(), get())
    }
}
