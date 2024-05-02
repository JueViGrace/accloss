package com.clo.accloss.user.di

import com.clo.accloss.user.data.local.UserLocalDataSource
import com.clo.accloss.user.data.remote.source.UserRemoteDataSource
import com.clo.accloss.user.domain.repository.UserRepository
import org.koin.dsl.module

val userModule = module {
    single {
        UserLocalDataSource(get(), get())
    }

    single {
        UserRemoteDataSource(get())
    }

    single {
        UserRepository(get(), get())
    }
}
