package com.clo.accloss.core.presentation.dashboard.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.dashboard.presentation.state.DashboardState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.session.domain.usecase.GetSession
import com.clo.accloss.user.domain.repository.UserRepository
import com.clo.accloss.vendedor.domain.repository.VendedorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getSession: GetSession,
    private val userRepository: UserRepository,
    private val vendedorRepository: VendedorRepository
) : ScreenModel {
    private var _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    var newSession: Session? by mutableStateOf(null)
        private set

    init {
        _state.update { dashboardState ->
            dashboardState.copy(
                session = RequestState.Loading
            )
        }

        screenModelScope.launch {
            getSession.invoke().collect { result ->
                _state.update { dashboardState ->
                    dashboardState.copy(
                        session = result
                    )
                }
                newSession = result.getSuccessDataSafe()
            }
        }
        getVendedores()
    }

    private fun getVendedores(){
        newSession?.let { session ->
            screenModelScope.launch {
                vendedorRepository.getVendedores(
                    baseUrl = session.enlaceEmpresa,
                    user = session.user,
                    empresa = session.empresa
                )
            }
        }
    }
}