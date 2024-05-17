package com.clo.accloss.billlines.data.source

import com.clo.accloss.billlines.data.local.BillLinesLocal
import com.clo.accloss.billlines.data.remote.source.BillLinesRemote

class BillLinesDataSourceImpl(
    override val billLinesRemote: BillLinesRemote,
    override val billLinesLocal: BillLinesLocal
) : BillLinesDataSource
