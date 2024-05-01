package com.clo.accloss.core.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dispatchersModule = module {
    single<CoroutineContext> { Dispatchers.IO }
    single<CoroutineScope> { CoroutineScope(get()) }
}
