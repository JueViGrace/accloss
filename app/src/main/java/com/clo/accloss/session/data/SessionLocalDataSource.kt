package com.clo.accloss.session.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.Session
import com.clo.accloss.core.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class SessionLocalDataSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getCurrentUser(): Flow<Session> = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getCurrentUser()
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    suspend fun addSession(session: Session) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .addSession(session)
        }
    }.await()

    suspend fun updateSession(session: Session) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries.updateSessions(
                active = session.active,
                user = session.user,
                empresa = session.empresa
            )
        }
    }.await()

    suspend fun deleteSession(session: Session) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries.deleteSession(
                user = session.user,
                empresa = session.empresa
            )
        }
    }.await()
}
