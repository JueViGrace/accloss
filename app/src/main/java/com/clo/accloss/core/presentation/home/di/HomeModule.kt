package com.clo.accloss.core.presentation.home.di

import com.clo.accloss.core.presentation.home.domain.GetRemoteData
import com.clo.accloss.core.presentation.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeViewModel(get())
    }
    singleOf(::GetRemoteData)
}
