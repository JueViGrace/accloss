package com.clo.accloss.user.di

import com.clo.accloss.user.data.local.UserLocal
import com.clo.accloss.user.data.local.UserLocalImpl
import com.clo.accloss.user.data.remote.source.UserRemote
import com.clo.accloss.user.data.remote.source.UserRemoteImpl
import com.clo.accloss.user.data.repository.UserRepositoryImpl
import com.clo.accloss.user.data.source.UserDataSource
import com.clo.accloss.user.data.source.UserDataSourceImpl
import com.clo.accloss.user.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    singleOf(::UserLocalImpl) bind UserLocal::class

    singleOf(::UserRemoteImpl) bind UserRemote::class

    singleOf(::UserDataSourceImpl) bind UserDataSource::class

    singleOf(::UserRepositoryImpl) bind UserRepository::class
}
