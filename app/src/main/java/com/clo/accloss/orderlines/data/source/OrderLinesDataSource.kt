package com.clo.accloss.orderlines.data.source

import com.clo.accloss.orderlines.data.local.OrderLinesLocal
import com.clo.accloss.orderlines.data.remote.source.OrderLinesRemote

interface OrderLinesDataSource {
    val orderLinesRemote: OrderLinesRemote
    val orderLinesLocal: OrderLinesLocal
}
