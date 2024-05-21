package com.clo.accloss.management.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.management.domain.usecase.GetManagementStatistics
import com.clo.accloss.management.presentation.state.ManagementDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ManagementDetailsViewModel(
    id: String,
    getManagementStatistics: GetManagementStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<ManagementDetailsState> = MutableStateFlow(ManagementDetailsState())
    val state = combine(
        _state,
        getManagementStatistics(id)
    ) { state, management ->
        state.copy(
            management = management
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ManagementDetailsState()
    )
}
