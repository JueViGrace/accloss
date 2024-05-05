package com.clo.accloss.pedido.di

import com.clo.accloss.pedido.data.local.PedidoLocalSource
import com.clo.accloss.pedido.data.remote.source.PedidoRemoteSource
import com.clo.accloss.pedido.domain.repository.PedidoRepository
import org.koin.dsl.module

val pedidoModule = module {
    single {
        PedidoLocalSource(get(), get())
    }
    single {
        PedidoRemoteSource(get())
    }
    single {
        PedidoRepository(get(), get())
    }
}
