package com.clo.accloss.core.presentation.contact.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.contact.presentation.state.ContactState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.usecase.GetSession
import com.clo.accloss.vendedor.domain.repository.VendedorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val getSession: GetSession,
    private val vendedorRepository: VendedorRepository
) : ScreenModel {
    private var _state: MutableStateFlow<ContactState> = MutableStateFlow(ContactState())
    val state: StateFlow<ContactState> = _state.asStateFlow()

    init {
        _state.update { contactState ->
            contactState.copy(
                currentSession = RequestState.Loading,
            )
        }

        screenModelScope.launch {
            getSession().collect { result ->
                when (result) {
                    is RequestState.Success -> {
                        _state.update { contactState ->
                            contactState.copy(
                                result
                            )
                        }
                        getVendedores()
                    }

                    else -> {
                        _state.update { contactState ->
                            contactState.copy(
                                result
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getVendedores() {
        screenModelScope.launch {
            vendedorRepository.getVendedores(
                baseUrl = _state.value.currentSession.getSuccessData().enlaceEmpresa,
                user = _state.value.currentSession.getSuccessData().user,
                empresa = _state.value.currentSession.getSuccessData().empresa,
                forceReload = _state.value.reload == true
            ).collect { vendedorResult ->
                _state.update { contactState ->
                    contactState.copy(
                        vendedores = vendedorResult,
                        reload = false
                    )
                }
            }
        }
    }

    fun onRefresh() {
        _state.update { contactState ->
            contactState.copy(
                reload = true
            )
        }
        getVendedores()
    }
}
