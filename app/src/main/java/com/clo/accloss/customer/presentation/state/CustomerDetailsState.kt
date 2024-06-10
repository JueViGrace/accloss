package com.clo.accloss.customer.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.customer.presentation.model.CustomerData

data class CustomerDetailsState(
    val customer: RequestState<CustomerData> = RequestState.Loading,
)
