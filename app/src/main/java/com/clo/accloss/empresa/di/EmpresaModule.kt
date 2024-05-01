package com.clo.accloss.empresa.di

import com.clo.accloss.empresa.data.local.EmpresaLocalDataSource
import com.clo.accloss.empresa.domain.repository.EmpresaRepository
import com.clo.accloss.empresa.data.remote.source.EmpresaRemoteDataSource
import org.koin.dsl.module

val empresaModule = module {
    single {
        EmpresaRemoteDataSource(get())
    }

    single {
        EmpresaLocalDataSource(get(), get())
    }

    single {
        EmpresaRepository(get(), get())
    }
}
