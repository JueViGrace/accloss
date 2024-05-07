package com.clo.accloss.core.presentation.profile.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.profile.presentation.state.ProfileState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionRepository: SessionRepository,
) : ScreenModel {
    private var _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        _state.update { profileState ->
            profileState.copy(
                currentSession = RequestState.Loading
            )
        }

        screenModelScope.launch {
            sessionRepository.getCurrentUser().collect { result ->
                _state.update { profileState ->
                    profileState.copy(
                        currentSession = result
                    )
                }
            }
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
