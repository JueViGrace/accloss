package com.clo.accloss.customer.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.customer.presentation.model.CustomerData

data class CustomersState(
    val customers: RequestState<List<CustomerData>> = RequestState.Loading,
    val searchBarVisible: Boolean = false,
    val searchText: String = ""
)
