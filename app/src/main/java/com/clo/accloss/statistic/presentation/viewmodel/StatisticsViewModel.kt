package com.clo.accloss.statistic.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.statistic.domain.usecase.GetStatistics
import com.clo.accloss.statistic.presentation.state.StatisticsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class StatisticsViewModel(
    getSalesmenStatistics: GetStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<StatisticsState> = MutableStateFlow(
        StatisticsState()
    )
    val state = combine(
        _state,
        getSalesmenStatistics()
    ) { state, salesmen ->
        when (salesmen) {
            is RequestState.Error -> {
                state.copy(
                    salesmen = emptyList(),
                    isLoading = false,
                    errorMessage = salesmen.message
                )
            }
            is RequestState.Success -> {
                state.copy(
                    salesmen = salesmen.data,
                    isLoading = false,
                    errorMessage = null
                )
            }
            else -> {
                state.copy(
                    salesmen = emptyList(),
                    isLoading = true,
                    errorMessage = null
                )
            }
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        StatisticsState()
    )
}