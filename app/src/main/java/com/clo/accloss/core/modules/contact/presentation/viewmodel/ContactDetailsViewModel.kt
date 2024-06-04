package com.clo.accloss.core.modules.contact.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.salesman.domain.usecase.GetSalesman
import com.clo.accloss.core.modules.contact.presentation.state.ContactDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ContactDetailsViewModel(
    getSalesman: GetSalesman,
    id: String
) : ScreenModel {
    private var _state: MutableStateFlow<ContactDetailsState> = MutableStateFlow(ContactDetailsState())
    val state = combine(
        _state,
        getSalesman(id)
    ) { state, salesman ->
        state.copy(
            salesman = salesman
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ContactDetailsState()
    )
}
