package com.clo.accloss.bills.presentation.model

import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.bills.domain.model.Bill

data class BillDetails(
    val bill: Bill? = null,
    val billLines: List<BillLines> = emptyList()
)
