package com.clo.accloss.login.di

import com.clo.accloss.login.presentation.viewmodel.LoginViewModel
import org.koin.dsl.module

val authModule = module {
    factory {
        LoginViewModel(get(), get(), get())
    }
}
