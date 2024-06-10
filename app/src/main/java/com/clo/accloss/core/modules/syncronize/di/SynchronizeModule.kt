package com.clo.accloss.core.modules.syncronize.di

import com.clo.accloss.core.modules.syncronize.domain.usecase.GetSynchronization
import com.clo.accloss.core.modules.syncronize.presentation.viewmodel.SynchronizeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val synchronizeModule = module {
    singleOf(::GetSynchronization)

    factoryOf(::SynchronizeViewModel)
}
