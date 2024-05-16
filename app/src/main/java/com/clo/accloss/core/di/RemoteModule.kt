package com.clo.accloss.core.di

import com.clo.accloss.core.data.network.KtorClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteModule = module {
    singleOf(::KtorClient)
}
