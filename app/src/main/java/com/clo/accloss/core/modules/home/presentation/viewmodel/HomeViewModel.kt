package com.clo.accloss.core.modules.home.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.modules.home.presentation.state.HomeState
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    getSession: GetCurrentUser
) : ScreenModel {
    private var _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        getSession()
    ) { state, session ->
        state.copy(
            currentSession = session
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        HomeState()
    )
}
