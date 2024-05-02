package com.clo.accloss.core.di

import com.clo.accloss.empresa.di.empresaModule
import com.clo.accloss.modules.auth.login.di.authModule
import com.clo.accloss.modules.home.di.homeModule
import com.clo.accloss.products.di.productModule
import com.clo.accloss.user.di.userModule
import org.koin.dsl.module
import com.clo.accloss.session.di.sessionModule

val sharedModule = module {
    includes(
        empresaModule,
        userModule,
        authModule,
        sessionModule,
        homeModule,
        productModule
    )
}
