package com.clo.accloss.bills.data.source

import com.clo.accloss.bills.data.local.BillLocal
import com.clo.accloss.bills.data.remote.source.BillRemote

interface BillDataSource {
    val billRemote: BillRemote
    val billLocal: BillLocal
}
