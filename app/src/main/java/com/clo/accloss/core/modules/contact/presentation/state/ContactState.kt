package com.clo.accloss.core.modules.contact.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman

data class ContactState(
    val salesmen: RequestState<List<Salesman>> = RequestState.Loading,
    val reload: Boolean? = null,
    val searchBarVisible: Boolean = false,
    val searchText: String = ""
)
