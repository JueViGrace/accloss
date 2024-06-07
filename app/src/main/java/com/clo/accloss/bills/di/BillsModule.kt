package com.clo.accloss.bills.di

import com.clo.accloss.bills.data.local.BillLocal
import com.clo.accloss.bills.data.local.BillLocalImpl
import com.clo.accloss.bills.data.remote.source.BillRemote
import com.clo.accloss.bills.data.remote.source.BillRemoteImpl
import com.clo.accloss.bills.data.repository.BillRepositoryImpl
import com.clo.accloss.bills.data.source.BillDataSource
import com.clo.accloss.bills.data.source.BillDataSourceImpl
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.bills.domain.usecase.GetBills
import com.clo.accloss.bills.presentation.viewmodel.BillsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val billModule = module {
    singleOf(::BillRemoteImpl) bind BillRemote::class

    singleOf(::BillLocalImpl) bind BillLocal::class

    singleOf(::BillDataSourceImpl) bind BillDataSource::class

    singleOf(::BillRepositoryImpl) bind BillRepository::class

    singleOf(::GetBills)

    factoryOf(::BillsViewModel)
}
