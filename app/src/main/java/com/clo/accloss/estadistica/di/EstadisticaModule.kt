package com.clo.accloss.estadistica.di

import com.clo.accloss.estadistica.data.local.EstadisticaLocalSource
import com.clo.accloss.estadistica.data.remote.source.EstadisticaRemoteSource
import com.clo.accloss.estadistica.domain.repository.EstadisticaRepository
import org.koin.dsl.module

val estadisticaModule = module {
    single {
        EstadisticaLocalSource(get(), get())
    }
    single {
        EstadisticaRemoteSource(get())
    }
    single {
        EstadisticaRepository(get(), get())
    }
}
