package com.clo.accloss.core.presentation.auth.login.presentation.events

import com.clo.accloss.session.domain.model.Session

sealed interface LoginEvents {
    data class OnEmpresaChanged(val value: String) : LoginEvents
    data object OnEmpresaClicked : LoginEvents
    data class OnUsernameChanged(val value: String) : LoginEvents
    data class OnPasswordChanged(val value: String) : LoginEvents
    data class OnSessionSelected(val session: Session) : LoginEvents
    data object OnLoginDismiss : LoginEvents
    data object OnLoginClicked : LoginEvents
}
