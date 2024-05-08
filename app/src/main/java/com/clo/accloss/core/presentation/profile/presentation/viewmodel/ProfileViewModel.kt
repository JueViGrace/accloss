package com.clo.accloss.core.presentation.profile.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.profile.presentation.state.ProfileState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionRepository: SessionRepository,
) : ScreenModel {
    private var _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    var newSession: Session? by mutableStateOf(null)
        private set

    init {
        _state.update { profileState ->
            profileState.copy(
                currentSession = RequestState.Loading,
                sessions = RequestState.Loading
            )
        }
        screenModelScope.launch {
            sessionRepository.getCurrentUser().collect { result ->
                _state.update { profileState ->
                    profileState.copy(
                        currentSession = result
                    )
                }
                newSession = if (result.isSuccess()) result.getSuccessData() else null
            }
        }

        screenModelScope.launch {
            sessionRepository.getSessions().collect { result ->
                _state.update { profileState ->
                    profileState.copy(
                        sessions = result
                    )
                }
            }
        }
    }

    fun changeSession(session: Session) {
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
