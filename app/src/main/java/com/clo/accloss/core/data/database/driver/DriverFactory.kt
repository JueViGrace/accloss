package com.clo.accloss.core.data.database.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.clo.accloss.ACCLOSSDB

class DriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(schema = ACCLOSSDB.Schema, context = context, "accloss.db")
    }
}
