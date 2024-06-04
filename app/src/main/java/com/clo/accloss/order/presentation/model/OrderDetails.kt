package com.clo.accloss.order.presentation.model

import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.orderlines.domain.model.OrderLines

data class OrderDetails(
    val order: Order? = null,
    val orderLines: List<OrderLines> = emptyList()
)
