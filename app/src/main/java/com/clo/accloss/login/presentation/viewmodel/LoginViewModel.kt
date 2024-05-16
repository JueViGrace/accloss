package com.clo.accloss.login.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.R
import com.clo.accloss.company.domain.repository.CompanyRepository
import com.clo.accloss.company.domain.rules.CompanyValidator
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.login.domain.model.Login
import com.clo.accloss.login.domain.rules.LoginValidator
import com.clo.accloss.login.presentation.events.LoginEvents
import com.clo.accloss.login.presentation.state.LoginState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val companyRepository: CompanyRepository,
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ScreenModel {
    private var _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    var newCompany: String? by mutableStateOf(null)
        private set

    var newLogin: Login? by mutableStateOf(null)
        private set

    init {
        newCompany = ""

        checkSession()
    }

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnCompanyChanged -> {
                newCompany = event.value
            }
            LoginEvents.OnCompanyClicked -> {
                newCompany?.let { company ->
                    val result = CompanyValidator.validateErrors(company)
                    val errors = listOfNotNull(
                        result.companyError
                    )

                    if (errors.isEmpty()) {
                        screenModelScope.launch {
                            companyRepository.getRemoteCompany(
                                code = company
                            ).collect { result ->
                                when (result) {
                                    is RequestState.Error -> {
                                        _state.update { loginState ->
                                            loginState.copy(
                                                errorMessage = result.message,
                                                loadingCompany = false
                                            )
                                        }
                                    }
                                    RequestState.Idle -> {}
                                    RequestState.Loading -> {
                                        _state.update { loginState ->
                                            loginState.copy(
                                                errorMessage = null,
                                                loadingCompany = true
                                            )
                                        }
                                    }
                                    is RequestState.Success -> {
                                        if (result.data.codigoEmpresa.isNotEmpty()) {
                                            _state.update { loginState ->
                                                loginState.copy(
                                                    company = result.data,
                                                    errorMessage = null,
                                                    loadingCompany = false,
                                                    canLogin = true,
                                                    companyError = null
                                                )
                                            }
                                            newLogin = Login()
                                        } else {
                                            _state.update { loginState ->
                                                loginState.copy(
                                                    company = null,
                                                    canLogin = false,
                                                    errorMessage = null,
                                                    loadingCompany = false,
                                                    companyError = R.string.incorrect_credentials,
                                                )
                                            }
                                            newLogin = null
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        _state.update { loginState ->
                            loginState.copy(
                                company = null,
                                companyError = result.companyError,
                                canLogin = false,
                                errorMessage = null,
                                loadingCompany = false,
                            )
                        }
                    }
                }
            }
            is LoginEvents.OnPasswordChanged -> {
                newLogin = newLogin?.copy(
                    password = event.value
                )
            }
            is LoginEvents.OnUsernameChanged -> {
                newLogin = newLogin?.copy(
                    username = event.value
                )
            }
            LoginEvents.OnLoginDismiss -> {
                _state.update { loginState ->
                    loginState.copy(
                        currentSession = RequestState.Idle,
                        company = null,
                        user = null,
                        loadingCompany = false,
                        loadingUser = false,
                        errorMessage = null,
                        canLogin = false,
                        companyError = null,
                        usernameError = null,
                        passwordError = null,
                    )
                }
                newLogin = null
                newCompany = null
            }
            LoginEvents.OnLoginClicked -> {
                newLogin?.let { login: Login ->
                    val result = LoginValidator.validateErrors(
                        username = login.username,
                        password = login.password
                    )
                    val errors = listOfNotNull(
                        result.usernameError,
                        result.passwordError
                    )

                    if (errors.isEmpty()) {
                        screenModelScope.launch {
                            _state.value.company?.let { company ->
                                val apiResult = userRepository.getRemoteUser(
                                    login.copy(
                                        baseUrl = "https://${company.enlaceEmpresa}"
                                    )
                                )
                                when (apiResult) {
                                    is RequestState.Error -> {
                                        _state.update { loginState ->
                                            loginState.copy(
                                                errorMessage = apiResult.message,
                                                loadingUser = false
                                            )
                                        }
                                    }
                                    is RequestState.Success -> {
                                        if (apiResult.data.username.isNotEmpty()) {
                                            val session = Session(
                                                nombre = apiResult.data.nombre,
                                                nombreEmpresa = company.nombreEmpresa,
                                                user = apiResult.data.vendedor,
                                                empresa = company.codigoEmpresa,
                                                enlaceEmpresa = "https://${company.enlaceEmpresa}",
                                                enlaceEmpresaPost = "http://${company.enlaceEmpresa}:5001",
                                                active = false
                                            )

                                            companyRepository.addCompany(
                                                company = company.copy(
                                                    enlaceEmpresa = "https://${company.enlaceEmpresa}/webservice",
                                                    enlaceEmpresaPost = "http://${company.enlaceEmpresa}:5001"
                                                )
                                            )

                                            userRepository.addUser(
                                                apiResult.data.copy(
                                                    empresa = company.codigoEmpresa,
                                                )
                                            )

                                            sessionRepository.addSession(session)

                                            sessionRepository.updateSession(session)

                                            _state.update { loginState ->
                                                loginState.copy(
                                                    user = apiResult.data,
                                                    errorMessage = null,
                                                    usernameError = null,
                                                    passwordError = null,
                                                    loadingUser = false
                                                )
                                            }
                                            checkSession()
                                        } else {
                                            _state.update { loginState ->
                                                loginState.copy(
                                                    passwordError = R.string.incorrect_credentials,
                                                    loadingUser = false
                                                )
                                            }
                                        }
                                    }
                                    else -> {
                                        _state.update { loginState ->
                                            loginState.copy(
                                                errorMessage = null,
                                                loadingUser = true
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        _state.update { loginState ->
                            loginState.copy(
                                errorMessage = null,
                                usernameError = result.usernameError,
                                passwordError = result.passwordError,
                                loadingUser = false
                            )
                        }
                    }
                }
            }

            is LoginEvents.OnSessionSelected -> {
                screenModelScope.launch {
                    sessionRepository.addSession(session = event.session.copy(active = true))
                    checkSession()
                }
            }
        }
    }

    private fun checkSession() {
        screenModelScope.launch {
            sessionRepository.getCurrentUser.collect { result ->
                _state.update { loginState ->
                    loginState.copy(
                        currentSession = result
                    )
                }
            }
        }

        screenModelScope.launch {
            sessionRepository.getSessions.collect { result ->
                _state.update { loginState ->
                    loginState.copy(
                        sessions = result
                    )
                }
            }
        }
    }
}
