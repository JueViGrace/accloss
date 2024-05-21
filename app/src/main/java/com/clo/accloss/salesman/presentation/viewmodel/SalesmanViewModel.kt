package com.clo.accloss.salesman.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.salesman.domain.usecase.GetSalesman
import com.clo.accloss.salesman.presentation.state.SalesmanState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SalesmanViewModel(
    getSalesman: GetSalesman,
    id: String
) : ScreenModel {
    private var _state: MutableStateFlow<SalesmanState> = MutableStateFlow(SalesmanState())
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
        SalesmanState()
    )
}
