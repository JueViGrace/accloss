package com.clo.accloss.core.modules.dashboard.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.modules.dashboard.presentation.state.DashboardState
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.rate.domain.usecase.GetRate
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    getSession: GetCurrentUser,
    private val getRates: GetRate
) : ScreenModel {
    private var _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        getSession(),
        getRates()
    ) { state, session, rates ->
        state.copy(
            currentSession = session,
            rates = rates
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        DashboardState()
    )

    fun updateRates() {
        screenModelScope.launch {
            _state.update { dashboardState ->
                dashboardState.copy(
                    rates = RequestState.Loading
                )
            }
            getRates().collect { result ->
                _state.update { dashboardState ->
                    dashboardState.copy(
                        rates = result
                    )
                }
            }
        }
    }
}
