package com.clo.accloss.home.di

import com.clo.accloss.home.presentation.viewmodel.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeViewModel(get())
    }
}
