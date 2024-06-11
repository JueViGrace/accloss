package com.clo.accloss.orderlines.di

import com.clo.accloss.orderlines.data.local.OrderLinesLocal
import com.clo.accloss.orderlines.data.local.DefaultOrderLinesLocal
import com.clo.accloss.orderlines.data.remote.source.OrderLinesRemote
import com.clo.accloss.orderlines.data.remote.source.DefaultOrderLinesRemote
import com.clo.accloss.orderlines.data.repository.DefaultOrderLinesRepository
import com.clo.accloss.orderlines.data.source.OrderLinesDataSource
import com.clo.accloss.orderlines.data.source.OrderLinesDataSourceImpl
import com.clo.accloss.orderlines.domain.repository.OrderLinesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val orderLinesModule = module {
    singleOf(::DefaultOrderLinesRemote) bind OrderLinesRemote::class

    singleOf(::DefaultOrderLinesLocal) bind OrderLinesLocal::class

    singleOf(::OrderLinesDataSourceImpl) bind OrderLinesDataSource::class

    singleOf(::DefaultOrderLinesRepository) bind OrderLinesRepository::class
}
