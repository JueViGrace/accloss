package com.clo.accloss.order.di

import com.clo.accloss.order.data.local.OrderLocal
import com.clo.accloss.order.data.local.DefaultOrderLocal
import com.clo.accloss.order.data.remote.source.OrderRemote
import com.clo.accloss.order.data.remote.source.DefaultOrderRemote
import com.clo.accloss.order.data.repository.DefaultOrderRepository
import com.clo.accloss.order.data.source.OrderDataSource
import com.clo.accloss.order.data.source.OrderDataSourceImpl
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.order.domain.usecase.GetOrder
import com.clo.accloss.order.domain.usecase.GetOrders
import com.clo.accloss.order.presentation.viewmodel.OrderDetailsViewModel
import com.clo.accloss.order.presentation.viewmodel.OrdersViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val orderModule = module {
    singleOf(::DefaultOrderRemote) bind OrderRemote::class

    singleOf(::DefaultOrderLocal) bind OrderLocal::class

    singleOf(::OrderDataSourceImpl) bind OrderDataSource::class

    singleOf(::DefaultOrderRepository) bind OrderRepository::class

    singleOf(::GetOrders)

    singleOf(::GetOrder)

    factoryOf(::OrdersViewModel)

    factoryOf(::OrderDetailsViewModel)
}
