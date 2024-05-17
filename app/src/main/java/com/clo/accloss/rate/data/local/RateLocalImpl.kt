package com.clo.accloss.rate.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Tasas as RateEntity

class RateLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : RateLocal {
    override suspend fun getRate(company: String): Flow<RateEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.tasasQueries
                .getTasa(
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addRate(rate: RateEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.tasasQueries.addTasa(tasas = rate)
        }
    }.await()

    override suspend fun deleteRate(company: String) = scope.async {
        dbHelper.withDatabase { db ->
            db.tasasQueries.deleteTasas(empresa = company)
        }
    }.await()
}
