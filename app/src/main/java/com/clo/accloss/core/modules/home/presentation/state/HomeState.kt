package com.clo.accloss.core.modules.home.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.model.Session

data class HomeState(
    val currentSession: RequestState<Session> = RequestState.Loading
)
