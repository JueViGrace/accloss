package com.clo.accloss.modules.auth.login.di

import com.clo.accloss.modules.auth.login.presentation.viewmodel.LoginViewModel
import org.koin.dsl.module

val authModule = module {
    factory {
        LoginViewModel(get(), get(), get())
    }
}
