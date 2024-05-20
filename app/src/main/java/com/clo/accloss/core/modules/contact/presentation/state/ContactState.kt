package com.clo.accloss.core.modules.contact.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman

data class ContactState(
    val sellers: RequestState<List<Salesman>> = RequestState.Loading,
    val reload: Boolean? = null
)
