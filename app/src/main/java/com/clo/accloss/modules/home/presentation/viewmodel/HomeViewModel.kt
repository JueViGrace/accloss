package com.clo.accloss.modules.home.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.modules.home.presentation.state.HomeState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionRepository: SessionRepository
) : ScreenModel {
    private var _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    var session: Session? by mutableStateOf(null)
        private set

    init {
        _state.update { homeState ->
            homeState.copy(
                session = RequestState.Loading
            )
        }

        screenModelScope.launch {
            sessionRepository.getCurrentUser().collect { result ->
                _state.update { homeState ->
                    homeState.copy(
                        session = result
                    )
                }
            }
        }
    }

    fun endSession() {
        screenModelScope.launch {
            if (_state.value.session.isSuccess()) {
                sessionRepository.deleteSession(_state.value.session.getSuccessData())
            }
        }
    }
}
