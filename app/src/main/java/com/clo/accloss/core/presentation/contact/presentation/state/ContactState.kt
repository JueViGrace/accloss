package com.clo.accloss.core.presentation.contact.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman

data class ContactState(
    val sellers: RequestState<List<Salesman>> = RequestState.Loading,
    val reload: Boolean? = null
)
