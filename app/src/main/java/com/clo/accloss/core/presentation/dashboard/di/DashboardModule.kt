package com.clo.accloss.core.presentation.dashboard.di

import com.clo.accloss.core.presentation.dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    factory {
        DashboardViewModel()
    }
}
