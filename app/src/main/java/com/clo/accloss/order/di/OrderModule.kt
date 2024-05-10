package com.clo.accloss.order.di

import com.clo.accloss.order.data.local.OrderLocalSource
import com.clo.accloss.order.data.remote.source.OrderRemoteSource
import com.clo.accloss.order.domain.repository.OrderRepository
import org.koin.dsl.module

val orderModule = module {
    single {
        OrderLocalSource(get(), get())
    }
    single {
        OrderRemoteSource(get())
    }
    single {
        OrderRepository(get(), get())
    }
}
