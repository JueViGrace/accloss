package com.clo.accloss.management.di

import com.clo.accloss.management.data.local.ManagementLocal
import com.clo.accloss.management.data.local.ManagementLocalImpl
import com.clo.accloss.management.data.remote.source.ManagementRemote
import com.clo.accloss.management.data.remote.source.ManagementRemoteImpl
import com.clo.accloss.management.data.repository.ManagementRepositoryImpl
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.data.source.ManagementDataSourceImpl
import com.clo.accloss.management.domain.repository.ManagementRepository
import com.clo.accloss.management.domain.usecase.GetManagementStatistics
import com.clo.accloss.management.domain.usecase.GetManagementsStatistics
import com.clo.accloss.management.presentation.viewmodel.ManagementViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val managementModule = module {
    singleOf(::ManagementRemoteImpl) bind ManagementRemote::class

    singleOf(::ManagementLocalImpl) bind ManagementLocal::class

    singleOf(::ManagementDataSourceImpl) bind ManagementDataSource::class

    singleOf(::ManagementRepositoryImpl) bind ManagementRepository::class

    singleOf(::GetManagementsStatistics)

    singleOf(::GetManagementStatistics)

    factoryOf(::ManagementViewModel)
}
