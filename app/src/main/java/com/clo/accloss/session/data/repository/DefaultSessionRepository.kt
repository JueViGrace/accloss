package com.clo.accloss.session.data.repository

import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.data.source.SessionDataSource
import com.clo.accloss.session.domain.mappers.toDatabase
import com.clo.accloss.session.domain.mappers.toDomain
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultSessionRepository(
    private val sessionDataSource: SessionDataSource,
    override val coroutineContext: CoroutineContext
) : SessionRepository {
    override val getCurrentUser: Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        sessionDataSource.getCurrentUser()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("SESSION REPOSITORY: getCurrentUser")
            }
            .collect { session ->
                if (session != null) {
                    emit(
                        RequestState.Success(
                            data = session.toDomain()
                        )
                    )
                } else {
                    emit(
                        RequestState.Error(
                            message = R.string.invalid_session
                        )
                    )
                }
            }
    }.flowOn(coroutineContext)

    override val getCurrentSession: Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        try {
            val session = sessionDataSource.getCurrentSession()

            if (session != null) {
                emit(
                    RequestState.Success(
                        data = session.toDomain()
                    )
                )
            } else {
                emit(
                    RequestState.Error(
                        message = R.string.session_is_not_available
                    )
                )
            }
        } catch (e: Exception) {
            e.log("SESSION REPOSITORY: getCurrentSession")
            emit(
                RequestState.Error(
                    message = DB_ERROR_MESSAGE
                )
            )
        }
    }.flowOn(coroutineContext)

    override val getSessions: Flow<RequestState<List<Session>>> = flow {
        emit(RequestState.Loading)

        try {
            val list = sessionDataSource.getSessions()

            emit(
                RequestState.Success(
                    data = list.map { session ->
                        session.toDomain()
                    }
                )
            )
        } catch (e: Exception) {
            e.log("SESSION REPOSITORY: getCurrentSession")
            emit(
                RequestState.Error(
                    message = DB_ERROR_MESSAGE
                )
            )
        }
    }.flowOn(coroutineContext)

    override suspend fun addSession(session: Session) {
        withContext(coroutineContext) {
            sessionDataSource.addSession(session.toDatabase())
        }
    }

    override suspend fun updateSession(session: Session) {
        withContext(coroutineContext) {
            sessionDataSource.updateSession(session.toDatabase())
        }
    }

    override suspend fun updateLastSync(lastSync: String, company: String) {
        withContext(coroutineContext) {
            sessionDataSource.updateLastSync(lastSync, company)
        }
    }

    override suspend fun deleteSession(session: Session) {
        withContext(coroutineContext) {
            sessionDataSource.deleteSession(session.toDatabase())
        }
    }
}
