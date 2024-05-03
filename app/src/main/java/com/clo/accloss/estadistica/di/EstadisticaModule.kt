package com.clo.accloss.estadistica.di

import com.clo.accloss.estadistica.data.local.EstadisticaLocalDataSource
import com.clo.accloss.estadistica.data.remote.source.EstadisticaRemoteDataSource
import com.clo.accloss.estadistica.domain.repository.EstadisticaRepository
import org.koin.dsl.module

val estadisticaModule = module {
    single {
        EstadisticaLocalDataSource(get(), get())
    }
    single {
        EstadisticaRemoteDataSource(get())
    }
    single {
        EstadisticaRepository(get(), get())
    }
}
