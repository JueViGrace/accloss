package com.clo.accloss.order.data.source

import com.clo.accloss.order.data.local.OrderLocal
import com.clo.accloss.order.data.remote.source.OrderRemote

interface OrderDataSource {
    val orderRemote: OrderRemote
    val orderLocal: OrderLocal
}