package com.clo.accloss.order.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.order.presentation.model.OrderDetails

data class OrderDetailsState(
    val order: RequestState<OrderDetails> = RequestState.Loading
)
