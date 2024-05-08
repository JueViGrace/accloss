package com.clo.accloss.core.presentation.contact.di

import com.clo.accloss.core.presentation.contact.presentation.viewmodel.ContactViewModel
import org.koin.dsl.module

val contactsModule = module {
    factory {
        ContactViewModel(get(), get())
    }
}
