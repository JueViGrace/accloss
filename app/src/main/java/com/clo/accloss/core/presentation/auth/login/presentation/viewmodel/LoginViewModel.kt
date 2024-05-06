package com.clo.accloss.core.presentation.auth.login.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.auth.login.domain.model.Login
import com.clo.accloss.core.presentation.auth.login.presentation.events.LoginEvents
import com.clo.accloss.core.presentation.auth.login.presentation.state.LoginState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.empresa.domain.repository.EmpresaRepository
import com.clo.accloss.empresa.domain.rules.EmpresaValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.user.domain.repository.UserRepository
import com.clo.accloss.user.domain.rules.UserValidator

class LoginViewModel(
    private val empresaRepository: EmpresaRepository,
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ScreenModel {
    private var _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    var newEmpresa: String? by mutableStateOf(null)
        private set

    var newLogin: Login? by mutableStateOf(null)
        private set

    init {
        newEmpresa = ""

        checkSession()
    }

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnEmpresaChanged -> {
                newEmpresa = event.value
            }
            LoginEvents.OnEmpresaClicked -> {
                newEmpresa?.let { codigo ->
                    val result = EmpresaValidator.validateErrors(codigo)
                    val errors = listOfNotNull(
                        result.codigoError
                    )

                    if (errors.isEmpty()) {
                        screenModelScope.launch {
                            empresaRepository.getRemoteEmpresa(
                                codigo = codigo
                            ).collect { result ->

                                when (result) {
                                    is RequestState.Error -> {
                                        _state.update { loginState ->
                                            loginState.copy(
                                                errorMessage = result.message,
                                                loadingEmpresa = false
                                            )
                                        }
                                    }
                                    RequestState.Idle -> {}
                                    RequestState.Loading -> {
                                        _state.update { loginState ->
                                            loginState.copy(
                                                errorMessage = null,
                                                loadingEmpresa = true
                                            )
                                        }
                                    }
                                    is RequestState.Success -> {
                                        if (result.data.codigoEmpresa.isNotEmpty()) {
                                            _state.update { loginState ->
                                                loginState.copy(
                                                    empresa = result.data,
                                                    errorMessage = null,
                                                    loadingEmpresa = false,
                                                    canLogin = true,
                                                    empresaError = null
                                                )
                                            }
                                            newLogin = Login()
                                        } else {
                                            _state.update { loginState ->
                                                loginState.copy(
                                                    empresa = null,
                                                    canLogin = false,
                                                    errorMessage = null,
                                                    loadingEmpresa = false,
                                                    empresaError = "Credenciales incorrectas",
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
                                empresa = null,
                                empresaError = result.codigoError,
                                canLogin = false,
                                errorMessage = null,
                                loadingEmpresa = false,
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
                        empresa = null,
                        user = null,
                        loadingEmpresa = false,
                        loadingUser = false,
                        errorMessage = null,
                        canLogin = false,
                        empresaError = null,
                        usernameError = null,
                        passwordError = null,
                    )
                }
                newLogin = null
                newEmpresa = null
            }
            LoginEvents.OnLoginClicked -> {
                newLogin?.let { login: Login ->
                    val result = UserValidator.validateErrors(login)
                    val errors = listOfNotNull(
                        result.usernameError,
                        result.passwordError
                    )

                    if (errors.isEmpty()) {
                        screenModelScope.launch {
                            _state.value.empresa?.let { empresa ->
                                userRepository.getRemoteUser(
                                    login.copy(
                                        baseUrl = "https://${empresa.enlaceEmpresa}"
                                    )
                                ).collect { result ->
                                    when (result) {
                                        is RequestState.Error -> {
                                            _state.update { loginState ->
                                                loginState.copy(
                                                    errorMessage = result.message,
                                                    loadingUser = false
                                                )
                                            }
                                        }
                                        is RequestState.Success -> {
                                            if (result.data.username.isNotEmpty()) {
                                                val session = Session(
                                                    nombre = result.data.nombre,
                                                    nombreEmpresa = empresa.nombreEmpresa,
                                                    user = result.data.vendedor,
                                                    empresa = empresa.codigoEmpresa,
                                                    enlaceEmpresa = "https://${empresa.enlaceEmpresa}",
                                                    enlaceEmpresaPost = "http://${empresa.enlaceEmpresa}:5001",
                                                    active = true
                                                )

                                                empresaRepository.addEmpresa(
                                                    empresa = empresa.copy(
                                                        enlaceEmpresa = "https://${empresa.enlaceEmpresa}/webservice",
                                                        enlaceEmpresaPost = "http://${empresa.enlaceEmpresa}:5001"
                                                    )
                                                )

                                                userRepository.addUser(
                                                    result.data.copy(
                                                        empresa = empresa.codigoEmpresa,
                                                    )
                                                )

                                                sessionRepository.addSession(session)

                                                sessionRepository.updateSession(session)

                                                _state.update { loginState ->
                                                    loginState.copy(
                                                        user = result.data,
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
                                                        errorMessage = "Credenciales incorrectas",
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
            sessionRepository.getCurrentUser().collect { result ->
                _state.update { loginState ->
                    loginState.copy(
                        currentSession = result
                    )
                }
            }
        }

        screenModelScope.launch {
            sessionRepository.getSessions().collect { result ->
                _state.update { loginState ->
                    loginState.copy(
                        sessions = result
                    )
                }
            }
        }
    }
}
