package com.clo.accloss.core.di

import com.clo.accloss.auth.login.di.authModule
import com.clo.accloss.empresa.di.empresaModule
import com.clo.accloss.home.di.homeModule
import org.koin.dsl.module
import session.di.sessionModule
import com.clo.accloss.user.di.userModule

val sharedModule = module {
    includes(
        empresaModule,
        userModule,
        authModule,
        sessionModule,
        homeModule
    )
}
