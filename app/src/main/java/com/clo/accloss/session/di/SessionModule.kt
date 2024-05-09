package com.clo.accloss.session.di

import com.clo.accloss.session.data.SessionLocalSource
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.session.domain.usecase.GetSession
import org.koin.dsl.module

val sessionModule = module {
    single {
        SessionLocalSource(get(), get())
    }
    single {
        SessionRepository(get())
    }
    single {
        GetSession(get())
    }
}
