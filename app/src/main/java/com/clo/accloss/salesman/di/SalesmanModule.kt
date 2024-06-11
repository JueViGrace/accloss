package com.clo.accloss.salesman.di

import com.clo.accloss.salesman.data.local.SalesmanLocal
import com.clo.accloss.salesman.data.local.DefaultSalesmanLocal
import com.clo.accloss.salesman.data.remote.source.SalesmanRemote
import com.clo.accloss.salesman.data.remote.source.DefaultSalesmanRemote
import com.clo.accloss.salesman.data.repository.DefaultSalesmanRepository
import com.clo.accloss.salesman.data.source.SalesmanDataSource
import com.clo.accloss.salesman.data.source.SalesmanDataSourceImpl
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.salesman.domain.usecase.GetSalesman
import com.clo.accloss.salesman.domain.usecase.GetSalesmen
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val salesmanModule = module {
    singleOf(::DefaultSalesmanLocal) bind SalesmanLocal::class

    singleOf(::DefaultSalesmanRemote) bind SalesmanRemote::class

    singleOf(::SalesmanDataSourceImpl) bind SalesmanDataSource::class

    singleOf(::DefaultSalesmanRepository) bind SalesmanRepository::class

    singleOf(::GetSalesmen)

    singleOf(::GetSalesman)
}
