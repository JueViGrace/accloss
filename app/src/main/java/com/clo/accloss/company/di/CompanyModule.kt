package com.clo.accloss.company.di

import com.clo.accloss.company.data.local.CompanyLocal
import com.clo.accloss.company.data.local.DefaultCompanyLocal
import com.clo.accloss.company.data.remote.source.CompanyRemote
import com.clo.accloss.company.data.remote.source.DefaultCompanyRemote
import com.clo.accloss.company.data.repository.DefaultCompanyRepository
import com.clo.accloss.company.data.source.CompanyDataSource
import com.clo.accloss.company.data.source.CompanyDataSourceImpl
import com.clo.accloss.company.domain.repository.CompanyRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val companyModule = module {
    singleOf(::DefaultCompanyRemote) bind CompanyRemote::class

    singleOf(::DefaultCompanyLocal) bind CompanyLocal::class

    singleOf(::CompanyDataSourceImpl) bind CompanyDataSource::class

    singleOf(::DefaultCompanyRepository) bind CompanyRepository::class
}
