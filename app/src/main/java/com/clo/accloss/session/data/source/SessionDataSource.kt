package com.clo.accloss.session.data.source

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Session as SessionEntity

interface SessionDataSource {
    val scope: CoroutineScope

    suspend fun getCurrentUser(): Flow<SessionEntity?>

    suspend fun getCurrentSession(): SessionEntity?

    suspend fun getSessions(): List<SessionEntity>

    suspend fun addSession(session: SessionEntity)

    suspend fun updateSession(session: SessionEntity)

    suspend fun updateLastSync(lastSync: String, company: String)

    suspend fun deleteSession(session: SessionEntity)
}
