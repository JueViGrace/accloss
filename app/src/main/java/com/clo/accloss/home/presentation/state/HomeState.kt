package com.clo.accloss.home.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.model.Session

data class HomeState(
    val session: RequestState<Session> = RequestState.Loading
)
