package com.clo.accloss.core.di

import com.clo.accloss.core.network.KtorClient
import org.koin.dsl.module

val remoteModule = module {
    single {
        KtorClient()
    }
}
