package com.clo.accloss.core.presentation.profile.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session

data class ProfileState(
    val currentSession: RequestState<Session> = RequestState.Idle
)
