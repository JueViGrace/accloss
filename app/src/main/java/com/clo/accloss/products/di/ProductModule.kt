package com.clo.accloss.products.di

import com.clo.accloss.products.data.local.ProductLocal
import com.clo.accloss.products.data.local.DefaultProductLocal
import com.clo.accloss.products.data.remote.source.ProductRemote
import com.clo.accloss.products.data.remote.source.DefaultProductRemote
import com.clo.accloss.products.data.repository.DefaultProductRepository
import com.clo.accloss.products.data.source.ProductDataSource
import com.clo.accloss.products.data.source.ProductDataSourceImpl
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.products.domain.usecase.GetProduct
import com.clo.accloss.products.domain.usecase.GetProducts
import com.clo.accloss.products.presentation.viewmodel.ProductDetailViewModel
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val productModule = module {
    singleOf(::DefaultProductLocal) bind ProductLocal::class

    singleOf(::DefaultProductRemote) bind ProductRemote::class

    singleOf(::ProductDataSourceImpl) bind ProductDataSource::class

    singleOf(::DefaultProductRepository) bind ProductRepository::class

    singleOf(::GetProducts)

    singleOf(::GetProduct)

    factoryOf(::ProductViewModel)

    factoryOf(::ProductDetailViewModel)
}
