package com.clo.accloss.session.domain.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.data.SessionLocalSource
import com.clo.accloss.session.domain.mappers.toDatabase
import com.clo.accloss.session.domain.mappers.toDomain
import com.clo.accloss.session.domain.model.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SessionRepository(
    private val sessionLocalSource: SessionLocalSource
) {
    fun getCurrentUser(): Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        sessionLocalSource.getCurrentUser()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { session ->
                emit(RequestState.Success(data = session.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    suspend fun addSession(session: Session) =
        sessionLocalSource.addSession(session.toDatabase())

    suspend fun updateSession(session: Session) =
        sessionLocalSource.updateSession(session.toDatabase())

    suspend fun deleteSession(session: Session) =
        sessionLocalSource.deleteSession(session.toDatabase())
}
