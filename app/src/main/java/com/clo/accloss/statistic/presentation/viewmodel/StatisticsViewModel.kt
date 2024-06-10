package com.clo.accloss.statistic.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.statistic.domain.usecase.GetSalesmenStatistics
import com.clo.accloss.statistic.presentation.state.StatisticsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class StatisticsViewModel(
    id: String,
    getSalesmenStatistics: GetSalesmenStatistics
) : ScreenModel {
    private var _state: MutableStateFlow<StatisticsState> = MutableStateFlow(
        StatisticsState()
    )
    val state = combine(
        _state,
        getSalesmenStatistics(id)
    ) { state, result ->
        when (result) {
            is RequestState.Success -> {
                if (state.searchText.isBlank()) {
                    state.copy(
                        salesmen = result,
                    )
                } else {
                    val data = result.data.filter { salesman ->
                        salesman.nombre.lowercase().contains(state.searchText.trim().lowercase()) ||
                            salesman.codigo.lowercase().contains(state.searchText.trim().lowercase()) ||
                            salesman.statistic?.nombrevend?.lowercase()?.contains(
                                state.searchText.trim().lowercase()
                            ) == true
                    }

                    state.copy(
                        salesmen = RequestState.Success(data)
                    )
                }
            }
            else -> {
                state.copy(
                    salesmen = result,
                )
            }
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        StatisticsState()
    )

    fun onSearchTextChange(text: String) {
        _state.update { productState ->
            productState.copy(
                searchText = text
            )
        }
    }

    fun toggleVisibility(force: Boolean? = null) {
        _state.update { productState ->
            productState.copy(
                searchBarVisible = force ?: productState.searchBarVisible
            )
        }
    }
}
