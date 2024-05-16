package com.clo.accloss.session.domain.repository

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    val getCurrentUser: Flow<RequestState<Session>>

    val getSessions: Flow<RequestState<List<Session>>>

    suspend fun addSession(session: Session)

    suspend fun updateSession(session: Session)

    suspend fun deleteSession(session: Session)
}
