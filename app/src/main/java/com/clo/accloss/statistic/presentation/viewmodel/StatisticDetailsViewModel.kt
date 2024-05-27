package com.clo.accloss.statistic.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.management.domain.usecase.GetManagementStatistics
import com.clo.accloss.statistic.domain.usecase.GetPersonalStatistics
import com.clo.accloss.statistic.presentation.state.StatisticDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class StatisticDetailsViewModel(
    private val id: String,
    private val getPersonalStatistics: GetPersonalStatistics,
    private val getManagementStatistics: GetManagementStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<StatisticDetailsState> = MutableStateFlow(StatisticDetailsState())
    val state = combine(
        _state,
        getPersonalStatistics(id),
        getManagementStatistics(id)
    ) { state, salesmanStatistics, managementStatistics ->
        if (id.startsWith("C")) {
            state.copy(
                personalStatistics = managementStatistics
            )
        } else {
            state.copy(
                personalStatistics = salesmanStatistics
            )
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        StatisticDetailsState()
    )
}
