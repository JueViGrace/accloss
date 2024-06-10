package com.clo.accloss.core.modules.dashboard.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.rate.domain.model.Rate
import com.clo.accloss.session.domain.model.Session

data class DashboardState(
    val currentSession: RequestState<Session> = RequestState.Loading,
    val rates: RequestState<Rate> = RequestState.Loading
)
