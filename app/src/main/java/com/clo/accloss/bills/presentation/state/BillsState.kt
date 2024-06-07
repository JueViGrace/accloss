package com.clo.accloss.bills.presentation.state

import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.core.domain.state.RequestState

data class BillsState(
    val bills: RequestState<List<Bill>> = RequestState.Loading,
    val searchText: String = "",
    val searchBarVisibility: Boolean = false
)
