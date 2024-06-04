package com.clo.accloss.customer.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.customer.domain.usecase.GetCustomerDetails
import com.clo.accloss.customer.presentation.state.CustomerDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CustomerDetailsViewModel(
    id: String,
    getCustomerDetails: GetCustomerDetails
) : ScreenModel {
    private var _state: MutableStateFlow<CustomerDetailsState> = MutableStateFlow(CustomerDetailsState())
    val state = combine(
        _state,
        getCustomerDetails(id)
    ) { state, customer ->
        state.copy(
            customer = customer
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        CustomerDetailsState()
    )
}
