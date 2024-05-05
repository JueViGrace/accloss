package com.clo.accloss.gerencia.di

import com.clo.accloss.gerencia.data.local.GerenciaLocalSource
import com.clo.accloss.gerencia.data.remote.source.GerenciaRemoteSource
import com.clo.accloss.gerencia.domain.repository.GerenciaRepository
import org.koin.dsl.module

val gerenciaModule = module {
    single {
        GerenciaLocalSource(get(), get())
    }
    single {
        GerenciaRemoteSource(get())
    }
    single {
        GerenciaRepository(get(), get())
    }
}
