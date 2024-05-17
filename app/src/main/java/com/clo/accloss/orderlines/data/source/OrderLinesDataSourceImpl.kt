package com.clo.accloss.orderlines.data.source

import com.clo.accloss.orderlines.data.local.OrderLinesLocal
import com.clo.accloss.orderlines.data.remote.source.OrderLinesRemote

class OrderLinesDataSourceImpl(
    override val orderLinesRemote: OrderLinesRemote,
    override val orderLinesLocal: OrderLinesLocal
) : OrderLinesDataSource
