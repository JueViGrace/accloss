package com.clo.accloss.vendedor.di

import com.clo.accloss.vendedor.data.local.VendedorLocalSource
import com.clo.accloss.vendedor.data.remote.source.VendedorRemoteSource
import com.clo.accloss.vendedor.domain.repository.VendedorRepository
import org.koin.dsl.module

val vendedorModule = module {
    single {
        VendedorLocalSource(get(), get())
    }
    single {
        VendedorRemoteSource(get())
    }
    single {
        VendedorRepository(get(), get())
    }
}
