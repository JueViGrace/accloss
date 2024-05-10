package com.clo.accloss.company.di

import com.clo.accloss.company.data.local.CompanyLocalSource
import com.clo.accloss.company.domain.repository.CompanyRepository
import com.clo.accloss.company.data.remote.source.CompanyRemoteSource
import org.koin.dsl.module

val companyModule = module {
    single {
        CompanyRemoteSource(get())
    }

    single {
        CompanyLocalSource(get(), get())
    }

    single {
        CompanyRepository(get(), get())
    }
}
