package com.clo.accloss.login.presentation.state

import com.clo.accloss.company.domain.model.Company
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.user.domain.model.User

data class LoginState(
    val currentSession: RequestState<Session> = RequestState.Idle,
    val sessions: RequestState<List<Session>> = RequestState.Idle,
    val company: Company? = null,
    val user: User? = null,
    val loadingCompany: Boolean = false,
    val loadingUser: Boolean = false,
    val errorMessage: Int? = null,
    val canLogin: Boolean = false,
    val companyError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
)
