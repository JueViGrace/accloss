package com.clo.accloss.management.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.management.presentation.state.ManagementState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class ManagementViewModel : ScreenModel {
    private var _state: MutableStateFlow<ManagementState> = MutableStateFlow(ManagementState())
    val state = combine(
        _state
    ) { state ->
        state
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ManagementState()
        )
}