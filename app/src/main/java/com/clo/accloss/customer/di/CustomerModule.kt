package com.clo.accloss.customer.di

import com.clo.accloss.customer.data.local.CustomerLocal
import com.clo.accloss.customer.data.local.DefaultCustomerLocal
import com.clo.accloss.customer.data.remote.source.CustomerRemote
import com.clo.accloss.customer.data.remote.source.CustomerRemoteImpl
import com.clo.accloss.customer.data.repository.DefaultCustomerRepository
import com.clo.accloss.customer.data.source.CustomerDataSource
import com.clo.accloss.customer.data.source.CustomerDataSourceImpl
import com.clo.accloss.customer.domain.repository.CustomerRepository
import com.clo.accloss.customer.domain.usecase.GetCustomerDetails
import com.clo.accloss.customer.domain.usecase.GetCustomers
import com.clo.accloss.customer.presentation.viewmodel.CustomerDetailsViewModel
import com.clo.accloss.customer.presentation.viewmodel.CustomersViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val customerModule = module {
    singleOf(::DefaultCustomerLocal) bind CustomerLocal::class

    singleOf(::CustomerRemoteImpl) bind CustomerRemote::class

    singleOf(::CustomerDataSourceImpl) bind CustomerDataSource::class

    singleOf(::DefaultCustomerRepository) bind CustomerRepository::class

    singleOf(::GetCustomers)

    singleOf(::GetCustomerDetails)

    factoryOf(::CustomersViewModel)

    factoryOf(::CustomerDetailsViewModel)
}
