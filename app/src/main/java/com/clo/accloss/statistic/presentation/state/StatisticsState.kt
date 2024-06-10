package com.clo.accloss.statistic.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.statistic.presentation.model.PersonalStatistics

data class StatisticsState(
    val salesmen: RequestState<List<PersonalStatistics>> = RequestState.Loading,
    val searchBarVisible: Boolean = false,
    val searchText: String = ""
)
