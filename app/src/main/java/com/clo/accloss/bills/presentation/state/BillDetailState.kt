package com.clo.accloss.bills.presentation.state

import com.clo.accloss.bills.presentation.model.BillDetails
import com.clo.accloss.core.state.RequestState

data class BillDetailState(
    val billDetails: RequestState<BillDetails> = RequestState.Loading
)
