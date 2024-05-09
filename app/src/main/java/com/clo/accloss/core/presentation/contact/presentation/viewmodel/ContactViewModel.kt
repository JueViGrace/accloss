package com.clo.accloss.core.presentation.contact.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.presentation.contact.presentation.state.ContactState
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.vendedor.domain.usecase.GetVendedores
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val getVendedores: GetVendedores
) : ScreenModel {
    private var _state: MutableStateFlow<ContactState> = MutableStateFlow(ContactState())
    val state = combine(
        _state,
        getVendedores(true)
    ) { state, result ->
        state.copy(
            vendedores = result,
        )
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ContactState()
        )

    private suspend fun updateVendedores() {
        getVendedores(
            forceReload = _state.value.reload == true
        ).collect { result ->
            _state.update { contactState ->
                contactState.copy(
                    vendedores = result,
                    reload = null
                )
            }
        }
    }

    fun onRefresh() {
        screenModelScope.launch {
            _state.update { contactState ->
                contactState.copy(
                    vendedores = RequestState.Loading,
                    reload = true
                )
            }
            delay(500)
            updateVendedores()
        }
    }
}
