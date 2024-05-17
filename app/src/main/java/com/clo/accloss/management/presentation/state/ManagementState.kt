package com.clo.accloss.management.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.management.presentation.model.ManagementsUi

data class ManagementState(
    val managements: RequestState<List<ManagementsUi>> = RequestState.Loading,
)
