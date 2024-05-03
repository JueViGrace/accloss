package com.clo.accloss.vendedor.di

import com.clo.accloss.vendedor.data.local.VendedorLocalDataSource
import com.clo.accloss.vendedor.data.remote.source.VendedorRemoteDataSource
import com.clo.accloss.vendedor.domain.repository.VendedorRepository
import org.koin.dsl.module

val vendedorModule = module {
    single {
        VendedorLocalDataSource(get(), get())
    }
    single {
        VendedorRemoteDataSource(get())
    }
    single {
        VendedorRepository(get(), get())
    }
}
