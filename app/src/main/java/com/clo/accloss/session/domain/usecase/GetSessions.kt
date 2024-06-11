package com.clo.accloss.session.domain.usecase

import com.clo.accloss.core.common.Constants.UNEXPECTED_ERROR
import com.clo.accloss.core.common.log
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetSessions(
    private val sessionRepository: SessionRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(): Flow<RequestState<List<Session>>> = flow {
        emit(RequestState.Loading)

        sessionRepository.getSessions
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = UNEXPECTED_ERROR
                    )
                )
                e.log("GET SESSIONS USE CASE")
            }
            .collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        emit(
                            RequestState.Error(
                                message = result.message
                            )
                        )
                    }
                    is RequestState.Success -> {
                        emit(
                            RequestState.Success(
                                data = result.data
                            )
                        )
                    }
                    else -> emit(RequestState.Loading)
                }
            }
    }.flowOn(coroutineContext)
}
