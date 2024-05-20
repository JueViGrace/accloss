package com.clo.accloss.session.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.session.data.source.SessionDataSource
import com.clo.accloss.session.domain.mappers.toDatabase
import com.clo.accloss.session.domain.mappers.toDomain
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SessionRepositoryImpl(
    private val sessionDataSource: SessionDataSource
) : SessionRepository {
    override val getCurrentUser: Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        sessionDataSource.getCurrentUser()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { session ->
                emit(RequestState.Success(data = session.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    override val getCurrentSession: Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        try {
            val session = sessionDataSource.getCurrentSession()

            if (session != null) {
                emit(RequestState.Success(data = session.toDomain()))
            } else {
                emit(RequestState.Error(message = "Session is not available"))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    override val getSessions: Flow<RequestState<List<Session>>> = flow {
        emit(RequestState.Loading)

        try {
            val list = sessionDataSource.getSessions()
            if (list.isNotEmpty()) {
                emit(
                    RequestState.Success(
                        data = list.map { session ->
                            session.toDomain()
                        }
                    )
                )
            } else {
                emit(RequestState.Success(data = emptyList()))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addSession(session: Session) = withContext(Dispatchers.IO) {
        sessionDataSource.addSession(session.toDatabase())
    }

    override suspend fun updateSession(session: Session) = withContext(Dispatchers.IO) {
        sessionDataSource.updateSession(session.toDatabase())
    }

    override suspend fun updateLastSync(lastSync: String, company: String) = withContext(Dispatchers.IO) {
        sessionDataSource.updateLastSync(lastSync, company)
    }

    override suspend fun deleteSession(session: Session) = withContext(Dispatchers.IO) {
        sessionDataSource.deleteSession(session.toDatabase())
    }
}
