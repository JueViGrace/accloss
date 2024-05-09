package com.clo.accloss.core.presentation.dashboard.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import com.clo.accloss.core.presentation.dashboard.presentation.state.DashboardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel() : ScreenModel {
    private var _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()
}
