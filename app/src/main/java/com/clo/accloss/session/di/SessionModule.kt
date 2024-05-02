package com.clo.accloss.session.di

import com.clo.accloss.session.data.SessionLocalDataSource
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.session.domain.usecase.GetSession
import org.koin.dsl.module

val sessionModule = module {
    single {
        SessionLocalDataSource(get(), get())
    }

    single {
        SessionRepository(get())
    }

    single {
        GetSession(get())
    }
}
