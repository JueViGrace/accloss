package com.clo.accloss.modules.home.di

import com.clo.accloss.modules.home.presentation.viewmodel.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeViewModel(get())
    }
}
