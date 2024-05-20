package com.clo.accloss.core.modules.profile.di

import com.clo.accloss.core.modules.profile.presentation.viewmodel.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    factory {
        ProfileViewModel(get(), get())
    }
}
