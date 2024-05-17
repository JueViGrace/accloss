package com.clo.accloss.order.di

import com.clo.accloss.order.data.local.OrderLocal
import com.clo.accloss.order.data.local.OrderLocalImpl
import com.clo.accloss.order.data.remote.source.OrderRemote
import com.clo.accloss.order.data.remote.source.OrderRemoteImpl
import com.clo.accloss.order.data.repository.OrderRepositoryImpl
import com.clo.accloss.order.data.source.OrderDataSource
import com.clo.accloss.order.data.source.OrderDataSourceImpl
import com.clo.accloss.order.domain.repository.OrderRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val orderModule = module {
    singleOf(::OrderRemoteImpl) bind OrderRemote::class

    singleOf(::OrderLocalImpl) bind OrderLocal::class

    singleOf(::OrderDataSourceImpl) bind OrderDataSource::class

    singleOf(::OrderRepositoryImpl) bind OrderRepository::class
}
