package com.clo.accloss.core.di

import com.clo.accloss.cliente.di.clienteModule
import com.clo.accloss.empresa.di.empresaModule
import com.clo.accloss.estadistica.di.estadisticaModule
import com.clo.accloss.gerencia.di.gerenciaModule
import com.clo.accloss.modules.auth.login.di.authModule
import com.clo.accloss.modules.home.di.homeModule
import com.clo.accloss.pedido.di.pedidoModule
import com.clo.accloss.products.di.productModule
import com.clo.accloss.session.di.sessionModule
import com.clo.accloss.user.di.userModule
import com.clo.accloss.vendedor.di.vendedorModule
import org.koin.dsl.module

val sharedModule = module {
    includes(
        empresaModule,
        userModule,
        authModule,
        sessionModule,
        homeModule,
        productModule,
        gerenciaModule,
        clienteModule,
        vendedorModule,
        estadisticaModule,
        pedidoModule
    )
}
