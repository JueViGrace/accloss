package com.clo.accloss.cliente.di

import com.clo.accloss.cliente.data.local.ClienteLocalSource
import com.clo.accloss.cliente.data.remote.source.ClienteRemoteSource
import com.clo.accloss.cliente.domain.repository.ClienteRepository
import org.koin.dsl.module

val clienteModule = module {
    single {
        ClienteLocalSource(get(), get())
    }
    single {
        ClienteRemoteSource(get())
    }
    single {
        ClienteRepository(get(), get())
    }
}
