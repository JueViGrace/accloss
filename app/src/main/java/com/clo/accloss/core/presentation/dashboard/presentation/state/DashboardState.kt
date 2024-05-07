package com.clo.accloss.core.presentation.dashboard.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.vendedor.domain.model.Vendedor

data class DashboardState(
    val session: RequestState<Session> = RequestState.Idle,
    val vendedores: RequestState<List<Vendedor>> = RequestState.Loading
)
