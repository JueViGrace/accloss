package com.clo.accloss.core.di

import com.clo.accloss.core.data.database.driver.DriverFactory
import com.clo.accloss.core.data.database.helper.DbHelper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::DriverFactory)

    singleOf(::DbHelper)
}
