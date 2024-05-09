package com.clo.accloss.core.presentation.contact.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.vendedor.domain.model.Vendedor

data class ContactState(
    val vendedores: RequestState<List<Vendedor>> = RequestState.Loading,
    val reload: Boolean? = null
)
