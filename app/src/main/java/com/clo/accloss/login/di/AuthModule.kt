package com.clo.accloss.login.di

import com.clo.accloss.login.presentation.viewmodel.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authModule = module {
    factoryOf(::LoginViewModel)
}
