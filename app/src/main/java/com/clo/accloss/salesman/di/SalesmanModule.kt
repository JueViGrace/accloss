package com.clo.accloss.salesman.di

import com.clo.accloss.salesman.data.local.SalesmanLocalSource
import com.clo.accloss.salesman.data.remote.source.SalesmanRemoteSource
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.salesman.domain.usecase.GetSellers
import org.koin.dsl.module

val salesmanModule = module {
    single {
        SalesmanLocalSource(get(), get())
    }
    single {
        SalesmanRemoteSource(get())
    }
    single {
        SalesmanRepository(get(), get())
    }
    single {
        GetSellers(get(), get())
    }
}
