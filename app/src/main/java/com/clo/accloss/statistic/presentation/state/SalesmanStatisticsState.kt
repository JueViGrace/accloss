package com.clo.accloss.statistic.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.statistic.domain.model.Statistic

data class SalesmanStatisticsState(
    val statistic: RequestState<Statistic> = RequestState.Loading,
)
