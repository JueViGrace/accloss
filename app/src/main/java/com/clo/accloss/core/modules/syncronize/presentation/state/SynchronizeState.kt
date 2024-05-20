package com.clo.accloss.core.modules.syncronize.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.core.modules.syncronize.presentation.model.Synchronize

data class SynchronizeState(
    val synchronize: RequestState<Synchronize> = RequestState.Idle,
)
