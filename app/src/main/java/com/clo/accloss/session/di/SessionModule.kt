package com.clo.accloss.session.di

import com.clo.accloss.session.data.local.SessionLocalSourceImpl
import com.clo.accloss.session.data.repository.SessionRepositoryImpl
import com.clo.accloss.session.data.source.SessionDataSource
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.session.domain.usecase.GetCurrentSession
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sessionModule = module {
    singleOf(::SessionLocalSourceImpl) bind SessionDataSource::class

    singleOf(::SessionRepositoryImpl) bind SessionRepository::class

    singleOf(::GetCurrentUser)

    singleOf(::GetCurrentSession)
}
