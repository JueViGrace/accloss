package com.clo.accloss.empresa.di

import com.clo.accloss.empresa.data.local.EmpresaLocalSource
import com.clo.accloss.empresa.domain.repository.EmpresaRepository
import com.clo.accloss.empresa.data.remote.source.EmpresaRemoteSource
import org.koin.dsl.module

val empresaModule = module {
    single {
        EmpresaRemoteSource(get())
    }

    single {
        EmpresaLocalSource(get(), get())
    }

    single {
        EmpresaRepository(get(), get())
    }
}
