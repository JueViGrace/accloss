package com.clo.accloss.customer.data.source

import com.clo.accloss.customer.data.local.CustomerLocal
import com.clo.accloss.customer.data.remote.source.CustomerRemote

interface CustomerDataSource {
    val customerRemote: CustomerRemote
    val customerLocal: CustomerLocal
}
