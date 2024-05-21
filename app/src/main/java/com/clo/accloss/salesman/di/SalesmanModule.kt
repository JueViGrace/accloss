package com.clo.accloss.salesman.di

import com.clo.accloss.salesman.data.local.SalesmanLocal
import com.clo.accloss.salesman.data.local.SalesmanLocalImpl
import com.clo.accloss.salesman.data.remote.source.SalesmanRemote
import com.clo.accloss.salesman.data.remote.source.SalesmanRemoteImpl
import com.clo.accloss.salesman.data.repository.SalesmanRepositoryImpl
import com.clo.accloss.salesman.data.source.SalesmanDataSource
import com.clo.accloss.salesman.data.source.SalesmanDataSourceImpl
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.salesman.domain.usecase.GetSalesman
import com.clo.accloss.salesman.domain.usecase.GetSalesmen
import com.clo.accloss.salesman.presentation.viewmodel.SalesmanViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val salesmanModule = module {
    singleOf(::SalesmanLocalImpl) bind SalesmanLocal::class

    singleOf(::SalesmanRemoteImpl) bind SalesmanRemote::class

    singleOf(::SalesmanDataSourceImpl) bind SalesmanDataSource::class

    singleOf(::SalesmanRepositoryImpl) bind SalesmanRepository::class

    singleOf(::GetSalesmen)

    singleOf(::GetSalesman)

    factoryOf(::SalesmanViewModel)
}
