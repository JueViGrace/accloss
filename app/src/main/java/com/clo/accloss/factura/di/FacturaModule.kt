package com.clo.accloss.factura.di

import com.clo.accloss.factura.data.local.FacturaLocalSource
import com.clo.accloss.factura.data.remote.source.FacturaRemoteSource
import com.clo.accloss.factura.domain.repository.FacturaRepository
import org.koin.dsl.module

val facturaModule = module {
    single {
        FacturaLocalSource(get(), get())
    }
    single {
        FacturaRemoteSource(get())
    }
    single {
        FacturaRepository(get(), get())
    }
}
