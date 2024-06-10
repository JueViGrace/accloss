package com.clo.accloss.bills.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.bills.domain.usecase.GetBill
import com.clo.accloss.bills.presentation.state.BillDetailState
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BillDetailViewModel(
    id: String,
    getBill: GetBill
) : ScreenModel {
    private var _state: MutableStateFlow<BillDetailState> = MutableStateFlow(BillDetailState())
    val state = combine(
        _state,
        getBill(id)
    ) { state, result ->
        state.copy(
            billDetails = result
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        BillDetailState()
    )
}
