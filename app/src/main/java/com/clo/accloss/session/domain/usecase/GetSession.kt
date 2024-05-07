package com.clo.accloss.session.domain.usecase

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSession(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<RequestState<Session>> = flow {
        sessionRepository.getCurrentUser()
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = e.message ?: DB_ERROR_MESSAGE
                    )
                )
            }
            .collect { result ->
                when (result) {
                    is RequestState.Success -> {
                        emit(
                            RequestState.Success(
                                data = result.data
                            )
                        )
                    }

                    RequestState.Loading -> {
                        emit(RequestState.Loading)
                    }

                    else -> {
                        emit(
                            RequestState.Error(
                                message = DB_ERROR_MESSAGE
                            )
                        )
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
}
