package com.clo.accloss.management.di

import com.clo.accloss.management.data.local.ManagementLocalSource
import com.clo.accloss.management.data.remote.source.ManagementRemoteSource
import com.clo.accloss.management.domain.repository.ManagementRepository
import org.koin.dsl.module

val managementModule = module {
    single {
        ManagementLocalSource(get(), get())
    }
    single {
        ManagementRemoteSource(get())
    }
    single {
        ManagementRepository(get(), get())
    }
}
