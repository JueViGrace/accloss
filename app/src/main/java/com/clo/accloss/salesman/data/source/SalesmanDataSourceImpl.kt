package com.clo.accloss.salesman.data.source

import com.clo.accloss.salesman.data.local.SalesmanLocal
import com.clo.accloss.salesman.data.remote.source.SalesmanRemote

class SalesmanDataSourceImpl(
    override val salesmanRemote: SalesmanRemote,
    override val salesmanLocal: SalesmanLocal,
) : SalesmanDataSource
