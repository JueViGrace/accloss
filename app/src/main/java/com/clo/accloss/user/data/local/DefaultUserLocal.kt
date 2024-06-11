package com.clo.accloss.user.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.User as UserEntity

class DefaultUserLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : UserLocal {

    override suspend fun getUsers(): Flow<List<UserEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.userQueries
                    .getUsers()
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getUser(code: String, company: String): Flow<UserEntity?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.userQueries
                    .getUser(vendedor = code, empresa = company)
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun addUser(user: UserEntity) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.userQueries.transaction {
                    db.userQueries.addUser(user = user)
                }
            }
        }.await()
    }

    override suspend fun updateSyncDate(
        lastSync: String,
        company: String
    ) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.userQueries.transaction {
                    db.userQueries.updateFechaSinc(ultSinc = lastSync, empresa = company)
                }
            }
        }.await()
    }

    override suspend fun deleteUser(user: String, company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.userQueries.transaction {
                    db.userQueries.deleteUser(vendedor = user, empresa = company)
                }
            }
        }.await()
    }
}
