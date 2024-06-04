package com.clo.accloss.customer.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.customer.domain.usecase.GetCustomers
import com.clo.accloss.customer.presentation.state.CustomersState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CustomersViewModel(
    id: String,
    getCustomers: GetCustomers
) : ScreenModel {
    private var _state: MutableStateFlow<CustomersState> = MutableStateFlow(CustomersState())
    val state = combine(
        _state,
        getCustomers(id)
    ) { state, result ->
        when (result) {
            is RequestState.Success -> {
                if (state.searchText.isBlank()) {
                    state.copy(
                        customers = result
                    )
                } else {
                    val data = result.data.filter { customerData ->
                        customerData.customer.codigo.lowercase().contains(state.searchText.trim().lowercase()) ||
                            customerData.customer.nombre.lowercase().contains(state.searchText.trim().lowercase())
                    }

                    state.copy(
                        customers = RequestState.Success(data)
                    )
                }
            }
            else -> {
                state.copy(
                    customers = result
                )
            }
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        CustomersState()
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
