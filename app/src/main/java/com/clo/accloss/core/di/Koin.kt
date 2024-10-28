package com.clo.accloss.core.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

object Koin {
    fun init(
        additionalModules: List<Module> = emptyList(),
        appDeclaration: KoinAppDeclaration = {}
    ) =
        startKoin {
            appDeclaration()
            modules(appModule() + additionalModules)
        }

    private fun appModule(): List<Module> {
        return listOf(databaseModule, dispatchersModule, remoteModule, sharedModule)
    }
}
