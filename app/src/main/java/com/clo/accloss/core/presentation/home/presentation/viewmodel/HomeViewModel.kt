package com.clo.accloss.core.presentation.home.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.home.presentation.state.HomeState
import com.clo.accloss.core.presentation.state.RequestState
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
    /*val state: StateFlow<HomeState> = combine(
        _state,
        sessionRepository.getCurrentUser(),
        sessionRepository.getSessions()
    ) { state, currentUser, sessions ->
        state.copy(
            currentSession = currentUser,
            sessions = sessions
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())*/

    init {
        _state.update { homeState ->
            homeState.copy(
                currentSession = RequestState.Loading,
                sessions = RequestState.Loading
            )
        }

        screenModelScope.launch {
            sessionRepository.getCurrentUser().collect { result ->
                _state.update { homeState ->
                    homeState.copy(
                        currentSession = result
                    )
                }
            }
        }

        screenModelScope.launch {
            sessionRepository.getSessions().collect { result ->
                _state.update { homeState ->
                    homeState.copy(
                        sessions = result
                    )
                }
            }
        }
    }

    fun onEvent(session: Session) {
        screenModelScope.launch {
            sessionRepository.updateSession(session)
            sessionRepository.addSession(session.copy(active = true))
        }
    }

    fun endSession() {
        screenModelScope.launch {
            if (_state.value.currentSession.isSuccess()) {
                sessionRepository.deleteSession(_state.value.currentSession.getSuccessData())
            }
        }
    }
}
