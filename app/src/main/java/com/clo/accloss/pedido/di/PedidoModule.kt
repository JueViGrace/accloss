package com.clo.accloss.pedido.di

import com.clo.accloss.pedido.data.local.PedidoLocalDataSource
import com.clo.accloss.pedido.data.remote.source.PedidoRemoteDataSource
import com.clo.accloss.pedido.domain.repository.PedidoRepository
import org.koin.dsl.module

val pedidoModule = module {
    single {
        PedidoLocalDataSource(get(), get())
    }
    single {
        PedidoRemoteDataSource(get())
    }
    single {
        PedidoRepository(get(), get())
    }
}
