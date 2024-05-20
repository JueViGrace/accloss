package com.clo.accloss.core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

class Koin {
    fun init(
        additionalModules: List<Module> = emptyList(),
        appDeclaration: KoinAppDeclaration = {}
    ) = startKoin {
        appDeclaration()
        modules(additionalModules + databaseModule + dispatchersModule + remoteModule + sharedModule)
    }
}
