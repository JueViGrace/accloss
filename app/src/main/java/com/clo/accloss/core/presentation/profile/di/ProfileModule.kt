package com.clo.accloss.core.presentation.profile.di

import com.clo.accloss.core.presentation.profile.presentation.viewmodel.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    factory {
        ProfileViewModel(get(), get())
    }
}
