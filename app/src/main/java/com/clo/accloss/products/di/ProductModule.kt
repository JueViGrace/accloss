package com.clo.accloss.products.di

import com.clo.accloss.products.data.local.ProductLocalDataSource
import com.clo.accloss.products.data.remote.source.ProductRemoteDataSource
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel
import org.koin.dsl.module

val productModule = module {
    single {
        ProductLocalDataSource(get(), get())
    }

    single {
        ProductRemoteDataSource(get())
    }

    single {
        ProductRepository(get(), get())
    }

    factory {
        ProductViewModel(get(), get())
    }
}
