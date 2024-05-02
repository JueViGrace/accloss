package com.clo.accloss.modules.auth.login.presentation.events

sealed interface LoginEvents {
    data class OnEmpresaChanged(val value: String) : LoginEvents
    data object OnEmpresaClicked : LoginEvents
    data class OnUsernameChanged(val value: String) : LoginEvents
    data class OnPasswordChanged(val value: String) : LoginEvents
    data object OnLoginDismiss : LoginEvents
    data object OnLoginClicked : LoginEvents
}
