package com.clo.accloss.session.data.source

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Session as SessionEntity

interface SessionDataSource {
    suspend fun getCurrentUser(): Flow<SessionEntity>

    suspend fun getSessions(): Flow<List<SessionEntity>>

    suspend fun addSession(session: SessionEntity)

    suspend fun updateSession(session: SessionEntity)

    suspend fun deleteSession(session: SessionEntity)
}
