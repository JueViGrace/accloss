package com.clo.accloss.customer.di

import com.clo.accloss.customer.data.local.CustomerLocal
import com.clo.accloss.customer.data.local.CustomerLocalImpl
import com.clo.accloss.customer.data.remote.source.CustomerRemote
import com.clo.accloss.customer.data.remote.source.CustomerRemoteImpl
import com.clo.accloss.customer.data.repository.CustomerRepositoryImpl
import com.clo.accloss.customer.data.source.CustomerDataSource
import com.clo.accloss.customer.data.source.CustomerDataSourceImpl
import com.clo.accloss.customer.domain.repository.CustomerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val customerModule = module {
    singleOf(::CustomerLocalImpl) bind CustomerLocal::class

    singleOf(::CustomerRemoteImpl) bind CustomerRemote::class

    singleOf(::CustomerDataSourceImpl) bind CustomerDataSource::class

    singleOf(::CustomerRepositoryImpl) bind CustomerRepository::class
}
