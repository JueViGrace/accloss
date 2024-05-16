package com.clo.accloss.session.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.presentation.state.RequestState
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

class SessionRepositoryImpl(
    private val sessionLocalSource: SessionDataSource
) : SessionRepository {
    override val getCurrentUser: Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        sessionLocalSource.getCurrentUser()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { session ->
                emit(RequestState.Success(data = session.toDomain()))
            }
    }.flowOn(Dispatchers.IO)

    override val getSessions: Flow<RequestState<List<Session>>> = flow<RequestState<List<Session>>> {
        emit(RequestState.Loading)

        sessionLocalSource.getSessions()
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { list ->
                emit(
                    RequestState.Success(
                        data = list.map { session ->
                            session.toDomain()
                        }
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addSession(session: Session) =
        sessionLocalSource.addSession(session.toDatabase())

    override suspend fun updateSession(session: Session) =
        sessionLocalSource.updateSession(session.toDatabase())

    override suspend fun deleteSession(session: Session) =
        sessionLocalSource.deleteSession(session.toDatabase())
}
