package com.clo.accloss.login.presentation.events

import com.clo.accloss.session.domain.model.Session

sealed interface LoginEvents {
    data class OnCompanyChanged(val value: String) : LoginEvents
    data object OnCompanyClicked : LoginEvents
    data class OnUsernameChanged(val value: String) : LoginEvents
    data class OnPasswordChanged(val value: String) : LoginEvents
    data class OnSessionSelected(val session: Session) : LoginEvents
    data object OnLoginDismiss : LoginEvents
    data object OnLoginClicked : LoginEvents
}
