package com.clo.accloss.company.di

import com.clo.accloss.company.data.local.CompanyLocal
import com.clo.accloss.company.data.local.CompanyLocalImpl
import com.clo.accloss.company.data.remote.source.CompanyRemote
import com.clo.accloss.company.data.remote.source.CompanyRemoteImpl
import com.clo.accloss.company.data.repository.CompanyRepositoryImpl
import com.clo.accloss.company.data.source.CompanyDataSource
import com.clo.accloss.company.data.source.CompanyDataSourceImpl
import com.clo.accloss.company.domain.repository.CompanyRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val companyModule = module {
    singleOf(::CompanyRemoteImpl) bind CompanyRemote::class

    singleOf(::CompanyLocalImpl) bind CompanyLocal::class

    singleOf(::CompanyDataSourceImpl) bind CompanyDataSource::class

    singleOf(::CompanyRepositoryImpl) bind CompanyRepository::class
}
