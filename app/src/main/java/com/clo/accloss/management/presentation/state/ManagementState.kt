package com.clo.accloss.management.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.statistic.presentation.model.PersonalStatistics

data class ManagementState(
    val managements: RequestState<List<PersonalStatistics>> = RequestState.Loading,
)
