package com.clo.accloss.user.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.User as UserEntity

class UserLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : UserLocal {

    override suspend fun getUsers(): Flow<List<UserEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries
                .getUsers()
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getUser(code: String, company: String): Flow<UserEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries
                .getUser(vendedor = code, empresa = company)
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addUser(user: UserEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries.addUser(user = user)
        }
    }.await()

    override suspend fun updateSyncDate(
        lastSync: String,
        company: String
    ) = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries.updateFechaSinc(ultSinc = lastSync, empresa = company)
        }
    }.await()
}
