package com.clo.accloss.core.modules.contact.di

import com.clo.accloss.core.modules.contact.presentation.viewmodel.ContactViewModel
import org.koin.dsl.module

val contactsModule = module {
    factory {
        ContactViewModel(get())
    }
}
