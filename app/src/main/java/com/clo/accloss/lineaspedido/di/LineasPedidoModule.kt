package com.clo.accloss.lineaspedido.di

import com.clo.accloss.lineaspedido.data.local.LineasPedidoLocalSource
import com.clo.accloss.lineaspedido.data.remote.source.LineasPedidoRemoteSource
import com.clo.accloss.lineaspedido.domain.repository.LineasPedidoRepository
import org.koin.dsl.module

val lineasPedidoModule = module {
    single {
        LineasPedidoRemoteSource(get())
    }
    single {
        LineasPedidoLocalSource(get(), get())
    }
    single {
        LineasPedidoRepository(get(), get())
    }
}
