package com.clo.accloss.bills.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.bills.domain.usecase.GetBills
import com.clo.accloss.bills.presentation.state.BillsState
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.domain.state.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class BillsViewModel(
    id: String,
    getBills: GetBills
) : ScreenModel {
    private var _state: MutableStateFlow<BillsState> = MutableStateFlow(BillsState())
    val state = combine(
        _state,
        getBills(id)
    ) { state, result ->
        when (result) {
            is RequestState.Success -> {
                if (state.searchText.isBlank()) {
                    state.copy(
                        bills = result
                    )
                } else {
                    val data = result.data.filter { bill ->
                        bill.documento.lowercase().contains(state.searchText.trim().lowercase()) ||
                            bill.codcliente.lowercase().contains(state.searchText.trim().lowercase()) ||
                            bill.nombrecli.lowercase().contains(state.searchText.trim().lowercase())
                    }

                    state.copy(
                        bills = RequestState.Success(data)
                    )
                }
            }
            else -> {
                state.copy(
                    bills = result
                )
            }
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        BillsState()
    )

    fun onSearchTextChange(text: String) {
        _state.update { billsState ->
            billsState.copy(
                searchText = text
            )
        }
    }

    fun toggleVisibility(force: Boolean? = null) {
        _state.update { billsState ->
            billsState.copy(
                searchBarVisibility = force ?: billsState.searchBarVisibility
            )
        }
    }
}
