package com.clo.accloss.session.di

import com.clo.accloss.session.data.local.DefaultSessionLocalSource
import com.clo.accloss.session.data.repository.DefaultSessionRepository
import com.clo.accloss.session.data.source.SessionDataSource
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.session.domain.usecase.DeleteSession
import com.clo.accloss.session.domain.usecase.GetCurrentSession
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import com.clo.accloss.session.domain.usecase.GetSessions
import com.clo.accloss.session.domain.usecase.UpdateSession
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sessionModule = module {
    singleOf(::DefaultSessionLocalSource) bind SessionDataSource::class

    singleOf(::DefaultSessionRepository) bind SessionRepository::class

    singleOf(::GetCurrentUser)

    singleOf(::GetCurrentSession)

    singleOf(::GetSessions)

    singleOf(::UpdateSession)

    singleOf(::DeleteSession)
}
