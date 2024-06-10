package com.clo.accloss.core.modules.contact.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman

data class ContactDetailsState(
    val salesman: RequestState<Salesman> = RequestState.Loading
)
