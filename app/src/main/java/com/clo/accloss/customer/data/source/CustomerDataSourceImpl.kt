package com.clo.accloss.customer.data.source

import com.clo.accloss.customer.data.local.CustomerLocal
import com.clo.accloss.customer.data.remote.source.CustomerRemote

class CustomerDataSourceImpl(
    override val customerRemote: CustomerRemote,
    override val customerLocal: CustomerLocal
) : CustomerDataSource
