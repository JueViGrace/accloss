package com.clo.accloss.core.modules.home.di

import com.clo.accloss.core.modules.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeModule = module {
    factoryOf(::HomeViewModel)
}
