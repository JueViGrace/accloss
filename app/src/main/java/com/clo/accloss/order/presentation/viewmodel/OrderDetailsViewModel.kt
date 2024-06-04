package com.clo.accloss.order.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.order.domain.usecase.GetOrder
import com.clo.accloss.order.presentation.state.OrderDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class OrderDetailsViewModel(
    orderId: String,
    getOrder: GetOrder
) : ScreenModel {
    private var _state = MutableStateFlow(OrderDetailsState())
    val state = combine(
        _state,
        getOrder(orderId)
    ) { state, order ->
        state.copy(
            order = order
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        OrderDetailsState()
    )
}
