package com.clo.accloss.order.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.order.domain.usecase.GetOrders
import com.clo.accloss.order.presentation.state.OrdersState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OrdersViewModel(
    id: String,
    getOrders: GetOrders
) : ScreenModel {
    private var _state: MutableStateFlow<OrdersState> = MutableStateFlow(OrdersState())
    val state = combine(
        _state,
        getOrders(id)
    ) { state, result ->
        when (result) {
            is RequestState.Success -> {
                if (state.searchText.isBlank()) {
                    state.copy(
                        orders = result
                    )
                } else {
                    val data = result.data.filter { order ->
                        order.ktiNdoc.lowercase().contains(state.searchText.trim().lowercase()) ||
                            order.ktiCodcli.lowercase().contains(state.searchText.trim().lowercase()) ||
                            order.ktiNroped.lowercase().contains(state.searchText.trim().lowercase())
                    }

                    state.copy(
                        orders = RequestState.Success(data)
                    )
                }
            }
            else -> {
                state.copy(
                    orders = result
                )
            }
        }
    }.stateIn(
        screenModelScope,
        Constants.SHARING_STARTED,
        OrdersState()
    )

    fun onSearchTextChange(text: String) {
        _state.update { ordersState ->
            ordersState.copy(
                searchText = text
            )
        }
    }

    fun toggleVisibility(force: Boolean? = null) {
        _state.update { ordersState ->
            ordersState.copy(
                searchBarVisible = force ?: ordersState.searchBarVisible
            )
        }
    }
}
