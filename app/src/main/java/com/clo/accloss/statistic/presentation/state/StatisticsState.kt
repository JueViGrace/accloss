package com.clo.accloss.statistic.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.statistic.presentation.model.PersonalStatistics

data class StatisticsState(
    val salesmen: RequestState<List<PersonalStatistics>> = RequestState.Loading,
)
