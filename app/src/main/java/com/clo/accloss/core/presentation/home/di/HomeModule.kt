package com.clo.accloss.core.presentation.home.di

import com.clo.accloss.core.presentation.home.presentation.viewmodel.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeViewModel(get())
    }
}
