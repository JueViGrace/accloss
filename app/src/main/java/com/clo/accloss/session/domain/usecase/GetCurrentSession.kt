package com.clo.accloss.session.domain.usecase

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class GetCurrentSession(
    private val sessionRepository: SessionRepository,
    private val coroutineContext: CoroutineContext
) {
    operator fun invoke(): Flow<RequestState<Session>> = flow {
        sessionRepository.getCurrentSession
            .catch { e ->
                emit(
                    RequestState.Error(
                        message = DB_ERROR_MESSAGE
                    )
                )
                e.log("GET CURRENT SESSION USE CASE")
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

                    is RequestState.Error -> {
                        emit(
                            RequestState.Error(
                                message = result.message
                            )
                        )
                    }

                    else -> {
                        emit(RequestState.Loading)
                    }
                }
            }
    }.flowOn(coroutineContext)
}
