package com.clo.accloss.bills.data.source

import com.clo.accloss.bills.data.local.BillLocal
import com.clo.accloss.bills.data.remote.source.BillRemote

class BillDataSourceImpl(
    override val billRemote: BillRemote,
    override val billLocal: BillLocal
) : BillDataSource
