package com.clo.accloss.statistic.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.statistic.domain.usecase.GetSalesmenStatistics
import com.clo.accloss.statistic.presentation.state.StatisticsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class StatisticsViewModel(
    getSalesmenStatistics: GetSalesmenStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<StatisticsState> = MutableStateFlow(
        StatisticsState()
    )
    val state = combine(
        _state,
        getSalesmenStatistics()
    ) { state, salesmen ->
        state.copy(
            salesmen = salesmen
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        StatisticsState()
    )
}
