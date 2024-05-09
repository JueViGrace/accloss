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

class UserLocalDataSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {

    suspend fun getUsers(): Flow<List<UserEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries
                .getUsers()
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getUser(codigo: String, empresa: String): Flow<UserEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries
                .getUser(vendedor = codigo, empresa = empresa)
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addUser(user: UserEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries.addUser(user = user)
        }
    }.await()
}
