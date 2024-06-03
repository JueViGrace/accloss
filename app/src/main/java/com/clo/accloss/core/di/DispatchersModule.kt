package com.clo.accloss.core.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dispatchersModule = module {
    single<CoroutineContext> { Dispatchers.IO }

    singleOf(::CoroutineScope) bind CoroutineScope::class
}
