package com.clo.accloss.statistic.presentation.state

import com.clo.accloss.statistic.domain.model.Statistic

data class StatisticsState(
    val salesmen: List<Statistic> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
