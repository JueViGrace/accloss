package com.clo.accloss.order.data.source

import com.clo.accloss.order.data.local.OrderLocal
import com.clo.accloss.order.data.remote.source.OrderRemote

class OrderDataSourceImpl(
    override val orderRemote: OrderRemote,
    override val orderLocal: OrderLocal
) : OrderDataSource
