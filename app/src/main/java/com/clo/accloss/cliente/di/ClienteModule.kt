package com.clo.accloss.cliente.di

import com.clo.accloss.cliente.data.local.ClienteLocalDataSource
import com.clo.accloss.cliente.data.remote.source.ClienteRemoteDataSource
import com.clo.accloss.cliente.domain.repository.ClienteRepository
import org.koin.dsl.module

val clienteModule = module {
    single {
        ClienteLocalDataSource(get(), get())
    }
    single {
        ClienteRemoteDataSource(get())
    }
    single {
        ClienteRepository(get(), get())
    }
}
