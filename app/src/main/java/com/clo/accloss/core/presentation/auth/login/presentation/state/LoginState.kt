package com.clo.accloss.core.presentation.auth.login.presentation.state

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.empresa.domain.model.Empresa
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.user.domain.model.User

data class LoginState(
    val currentSession: RequestState<Session> = RequestState.Idle,
    val sessions: RequestState<List<Session>> = RequestState.Loading,
    val empresa: Empresa? = null,
    val user: User? = null,
    val loadingEmpresa: Boolean = false,
    val loadingUser: Boolean = false,
    val errorMessage: String? = null,
    val canLogin: Boolean = false,
    val empresaError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
)
