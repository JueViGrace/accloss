package com.clo.accloss.core.presentation.auth.login.di

import com.clo.accloss.core.presentation.auth.login.presentation.viewmodel.LoginViewModel
import org.koin.dsl.module

val authModule = module {
    factory {
        LoginViewModel(get(), get(), get())
    }
}
