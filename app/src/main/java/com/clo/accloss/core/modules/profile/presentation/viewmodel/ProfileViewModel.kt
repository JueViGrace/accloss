package com.clo.accloss.core.modules.profile.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.modules.profile.presentation.state.ProfileState
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.statistic.domain.usecase.GetProfileStatistics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionRepository: SessionRepository,
    private val getProfileStatistics: GetProfileStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        getProfileStatistics()
    ) { state, profileStatistics ->
        state.copy(
            profileStatistics = profileStatistics
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ProfileState()
    )

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
            sessionRepository.getCurrentUser.collect { result ->
                _state.update { profileState ->
                    profileState.copy(
                        currentSession = result
                    )
                }
                newSession = if (result.isSuccess()) result.getSuccessData() else null
            }
        }

        screenModelScope.launch {
            sessionRepository.getSessions.collect { result ->
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
