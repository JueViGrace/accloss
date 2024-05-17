package com.clo.accloss.billlines.data.source

import com.clo.accloss.billlines.data.local.BillLinesLocal
import com.clo.accloss.billlines.data.remote.source.BillLinesRemote

interface BillLinesDataSource {
    val billLinesRemote: BillLinesRemote
    val billLinesLocal: BillLinesLocal
}
