package com.clo.accloss.statistic.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.statistic.presentation.model.PersonalStatistics

data class StatisticDetailsState(
    val personalStatistics: RequestState<PersonalStatistics> = RequestState.Loading,
)
