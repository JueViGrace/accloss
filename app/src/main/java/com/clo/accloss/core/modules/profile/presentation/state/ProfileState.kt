package com.clo.accloss.core.modules.profile.presentation.state

import com.clo.accloss.core.modules.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.session.domain.model.Session

data class ProfileState(
    val currentSession: RequestState<Session> = RequestState.Loading,
    val sessions: RequestState<List<Session>> = RequestState.Loading,
    val profileStatistics: RequestState<ProfileStatisticsModel> = RequestState.Loading
)
