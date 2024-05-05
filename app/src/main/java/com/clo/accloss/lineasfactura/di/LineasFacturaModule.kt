package com.clo.accloss.lineasfactura.di

import com.clo.accloss.lineasfactura.data.local.LineasFacturaLocalSource
import com.clo.accloss.lineasfactura.data.remote.source.LineasFacturaRemoteSource
import com.clo.accloss.lineasfactura.domain.repository.LineasFacturaRepository
import org.koin.dsl.module

val lineasFacturaModule = module {
    single {
        LineasFacturaRemoteSource(get())
    }
    single {
        LineasFacturaLocalSource(get(), get())
    }
    single {
        LineasFacturaRepository(get(), get())
    }
}
