package com.clo.accloss.products.di

import com.clo.accloss.products.data.local.ProductLocalSource
import com.clo.accloss.products.data.remote.source.ProductRemoteSource
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel
import org.koin.dsl.module

val productModule = module {
    single {
        ProductLocalSource(get(), get())
    }

    single {
        ProductRemoteSource(get())
    }

    single {
        ProductRepository(get(), get())
    }

    factory {
        ProductViewModel(get(), get())
    }
}
