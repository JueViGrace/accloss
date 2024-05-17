package com.clo.accloss.core.presentation.home.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.presentation.home.domain.GetRemoteData
import com.clo.accloss.core.presentation.home.presentation.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getRemoteData: GetRemoteData
) : ScreenModel {
    private var _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        getRemoteData()
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
