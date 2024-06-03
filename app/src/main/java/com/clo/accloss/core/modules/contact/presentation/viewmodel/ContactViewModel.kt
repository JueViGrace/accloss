package com.clo.accloss.core.modules.contact.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.core.modules.contact.presentation.state.ContactState
import com.clo.accloss.salesman.domain.usecase.GetSalesmen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ContactViewModel(
    getSalesmen: GetSalesmen
) : ScreenModel {
    private var _state: MutableStateFlow<ContactState> = MutableStateFlow(ContactState())
    val state = combine(
        _state,
        getSalesmen()
    ) { state, result ->
        when (result) {
            is RequestState.Success -> {
                if (state.searchText.isBlank()) {
                    state.copy(
                        salesmen = result,
                    )
                } else {
                    val data = result.data.filter { salesman ->
                        salesman.nombre.lowercase().contains(state.searchText.trim().lowercase()) ||
                            salesman.vendedor.lowercase().contains(state.searchText.trim().lowercase())
                    }

                    state.copy(
                        salesmen = RequestState.Success(data)
                    )
                }
            }
            else -> {
                state.copy(
                    salesmen = result,
                )
            }
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ContactState()
    )

    fun onSearchTextChange(text: String) {
        _state.update { productState ->
            productState.copy(
                searchText = text
            )
        }
    }

    fun toggleVisibility(force: Boolean? = null) {
        _state.update { productState ->
            productState.copy(
                searchBarVisible = force ?: productState.searchBarVisible
            )
        }
    }
}
