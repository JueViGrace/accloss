package com.clo.accloss.core.modules.contact.di

import com.clo.accloss.core.modules.contact.presentation.viewmodel.ContactDetailsViewModel
import com.clo.accloss.core.modules.contact.presentation.viewmodel.ContactViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val contactsModule = module {
    factoryOf(::ContactViewModel)

    factoryOf(::ContactDetailsViewModel)
}
