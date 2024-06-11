package com.clo.accloss.management.di

import com.clo.accloss.management.data.local.ManagementLocal
import com.clo.accloss.management.data.local.DefaultManagementLocal
import com.clo.accloss.management.data.remote.source.ManagementRemote
import com.clo.accloss.management.data.remote.source.DefaultManagementRemote
import com.clo.accloss.management.data.repository.DefaultManagementRepository
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.data.source.ManagementDataSourceImpl
import com.clo.accloss.management.domain.repository.ManagementRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val managementModule = module {
    singleOf(::DefaultManagementRemote) bind ManagementRemote::class

    singleOf(::DefaultManagementLocal) bind ManagementLocal::class

    singleOf(::ManagementDataSourceImpl) bind ManagementDataSource::class

    singleOf(::DefaultManagementRepository) bind ManagementRepository::class
}
