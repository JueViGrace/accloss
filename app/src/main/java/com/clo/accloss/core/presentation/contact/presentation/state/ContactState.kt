package com.clo.accloss.core.presentation.contact.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.vendedor.domain.model.Vendedor

data class ContactState(
    val currentSession: RequestState<Session> = RequestState.Idle,
    val vendedores: RequestState<List<Vendedor>> = RequestState.Idle,
    val reload: Boolean? = null
)
