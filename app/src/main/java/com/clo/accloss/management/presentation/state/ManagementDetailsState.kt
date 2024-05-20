package com.clo.accloss.management.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.management.presentation.model.ManagementsUi

data class ManagementDetailsState(
    val management: RequestState<ManagementsUi> = RequestState.Loading
)
