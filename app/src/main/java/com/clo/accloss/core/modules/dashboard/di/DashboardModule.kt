package com.clo.accloss.core.modules.dashboard.di

import com.clo.accloss.core.modules.dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    factory {
        DashboardViewModel(get(), get())
    }
}
