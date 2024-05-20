package com.clo.accloss.core.modules.syncronize.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.domain.usecase.GetSynchronization
import com.clo.accloss.core.modules.syncronize.presentation.state.SynchronizeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SynchronizeViewModel(
    private val getSynchronization: GetSynchronization,
) : ScreenModel {
    private var _state: MutableStateFlow<SynchronizeState> = MutableStateFlow(SynchronizeState())
    val state: StateFlow<SynchronizeState> = _state.asStateFlow()

    fun synchronize() {
        screenModelScope.launch {
            getSynchronization().collect { result ->
                _state.update { synchronizeState ->
                    synchronizeState.copy(
                        synchronize = result,
                    )
                }
            }
        }
    }
}
