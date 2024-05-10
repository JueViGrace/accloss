package com.clo.accloss.core.presentation.dashboard.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.dashboard.presentation.state.DashboardState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.rate.domain.usecase.GetRate
import com.clo.accloss.session.domain.usecase.GetSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    getSession: GetSession,
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
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000L),
            DashboardState()
        )

    fun updateRates() {
        _state.update { dashboardState ->
            dashboardState.copy(
                rates = RequestState.Loading
            )
        }

        screenModelScope.launch {
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
