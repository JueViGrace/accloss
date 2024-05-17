package com.clo.accloss.management.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.management.domain.usecase.GetManagementsStatistics
import com.clo.accloss.management.presentation.state.ManagementState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class ManagementViewModel(
    private val getManagementsStatistics: GetManagementsStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<ManagementState> = MutableStateFlow(ManagementState())
    val state = combine(
        _state,
        getManagementsStatistics()
    ) { state, managements ->
        state.copy(
            managements = managements
        )
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            screenModelScope,
            SHARING_STARTED,
            ManagementState()
        )
}
