package com.clo.accloss.salesman.data.source

import com.clo.accloss.salesman.data.local.SalesmanLocal
import com.clo.accloss.salesman.data.remote.source.SalesmanRemote

interface SalesmanDataSource {
    val salesmanRemote: SalesmanRemote
    val salesmanLocal: SalesmanLocal
}