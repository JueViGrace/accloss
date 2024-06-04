package com.clo.accloss.order.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.order.domain.model.Order

data class OrdersState(
    val orders: RequestState<List<Order>> = RequestState.Loading,
    val searchBarVisible: Boolean = false,
    val searchText: String = ""
)
