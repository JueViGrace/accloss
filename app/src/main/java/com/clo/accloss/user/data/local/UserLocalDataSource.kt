package com.clo.accloss.user.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.User
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class UserLocalDataSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {

    suspend fun getUsers(): Flow<List<User>> = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries
                .getUsers()
                .asFlow()
                .mapToList(scope.coroutineContext)
        }
    }.await()

    suspend fun getUser(codigo: String, empresa: String): Flow<User> = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries
                .getUser(vendedor = codigo, empresa = empresa)
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    suspend fun addUser(user: User) = scope.async {
        dbHelper.withDatabase { db ->
            db.userQueries.addUser(user = user)
        }
    }.await()
}
