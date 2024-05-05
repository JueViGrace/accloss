package com.clo.accloss.core.data.database.helper

import com.clo.accloss.ACCLOSSDB
import com.clo.accloss.core.data.database.driver.DriverFactory
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DbHelper(private val driverFactory: DriverFactory) {
    private var db: ACCLOSSDB? = null

    private val mutex = Mutex()

    suspend fun <Result : Any> withDatabase(block: suspend (ACCLOSSDB) -> Result): Result = mutex.withLock {
        if (db == null) {
            db = createDb(driverFactory)
        }

        return@withLock block(db!!)
    }

    private fun createDb(driverFactory: DriverFactory): ACCLOSSDB {
        return ACCLOSSDB(driver = driverFactory.createDriver())
    }
}
