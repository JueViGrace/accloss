package com.clo.accloss.salesman.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman

data class SalesmanState(
    val salesman: RequestState<Salesman> = RequestState.Loading
)
