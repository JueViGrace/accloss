package com.clo.accloss.core.modules.profile.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.modules.profile.presentation.state.ProfileState
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.usecase.DeleteSession
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import com.clo.accloss.session.domain.usecase.GetSessions
import com.clo.accloss.session.domain.usecase.UpdateSession
import com.clo.accloss.statistic.domain.usecase.GetProfileStatistics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    getCurrentUser: GetCurrentUser,
    getSessions: GetSessions,
    getProfileStatistics: GetProfileStatistics,
    private val updateSession: UpdateSession,
    private val deleteSession: DeleteSession,
) : ScreenModel {
    private var _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        getProfileStatistics()
    ) { state, profileStatistics ->
        state.copy(
            profileStatistics = profileStatistics,
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ProfileState()
    )

    init {
        _state.update { profileState ->
            profileState.copy(
                currentSession = RequestState.Loading,
                sessions = RequestState.Loading
            )
        }
        screenModelScope.launch {
            getCurrentUser().collect { result ->
                _state.update { profileState ->
                    profileState.copy(
                        currentSession = result
                    )
                }
            }
        }

        screenModelScope.launch {
            getSessions().collect { result ->
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
            updateSession(session)
        }
    }

    fun endSession() {
        screenModelScope.launch {
            if (_state.value.currentSession.isSuccess()) {
                deleteSession(_state.value.currentSession.getSuccessData())
            }
        }
    }
}
