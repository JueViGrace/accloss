package com.clo.accloss.user.di

import com.clo.accloss.user.data.local.UserLocal
import com.clo.accloss.user.data.local.DefaultUserLocal
import com.clo.accloss.user.data.remote.source.UserRemote
import com.clo.accloss.user.data.remote.source.DefaultUserRemote
import com.clo.accloss.user.data.repository.DefaultUserRepository
import com.clo.accloss.user.data.source.UserDataSource
import com.clo.accloss.user.data.source.UserDataSourceImpl
import com.clo.accloss.user.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    singleOf(::DefaultUserLocal) bind UserLocal::class

    singleOf(::DefaultUserRemote) bind UserRemote::class

    singleOf(::UserDataSourceImpl) bind UserDataSource::class

    singleOf(::DefaultUserRepository) bind UserRepository::class
}
