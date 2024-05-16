package com.clo.accloss.products.di

import com.clo.accloss.products.data.local.ProductLocal
import com.clo.accloss.products.data.local.ProductLocalImpl
import com.clo.accloss.products.data.remote.source.ProductRemote
import com.clo.accloss.products.data.remote.source.ProductRemoteImpl
import com.clo.accloss.products.data.repository.ProductRepositoryImpl
import com.clo.accloss.products.data.source.ProductDataSource
import com.clo.accloss.products.data.source.ProductDataSourceImpl
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.products.domain.usecase.GetProducts
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val productModule = module {
    singleOf(::ProductLocalImpl) bind ProductLocal::class

    singleOf(::ProductRemoteImpl) bind ProductRemote::class

    singleOf(::ProductDataSourceImpl) bind ProductDataSource::class

    singleOf(::ProductRepositoryImpl) bind ProductRepository::class

    singleOf(::GetProducts)

    factory {
        ProductViewModel(get())
    }
}
