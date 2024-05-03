package com.clo.accloss.gerencia.di

import com.clo.accloss.gerencia.data.local.GerenciaLocalDataSource
import com.clo.accloss.gerencia.data.remote.source.GerenciaRemoteDataSource
import com.clo.accloss.gerencia.domain.repository.GerenciaRepository
import org.koin.dsl.module

val gerenciaModule = module {
    single {
        GerenciaLocalDataSource(get(), get())
    }
    single {
        GerenciaRemoteDataSource(get())
    }
    single {
        GerenciaRepository(get(), get())
    }
}
