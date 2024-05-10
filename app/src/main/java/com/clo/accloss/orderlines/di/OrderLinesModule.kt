package com.clo.accloss.orderlines.di

import com.clo.accloss.orderlines.data.local.OrderLinesLocalSource
import com.clo.accloss.orderlines.data.remote.source.OrderLinesRemoteSource
import com.clo.accloss.orderlines.domain.repository.OrderLinesRepository
import org.koin.dsl.module

val orderLinesModule = module {
    single {
        OrderLinesRemoteSource(get())
    }
    single {
        OrderLinesLocalSource(get(), get())
    }
    single {
        OrderLinesRepository(get(), get())
    }
}
