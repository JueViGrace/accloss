package com.clo.accloss.session.domain.usecase

import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UpdateSession(
    private val sessionRepository: SessionRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend operator fun invoke(session: Session) {
        withContext(coroutineContext) {
            sessionRepository.updateSession(session)
        }
    }
}
