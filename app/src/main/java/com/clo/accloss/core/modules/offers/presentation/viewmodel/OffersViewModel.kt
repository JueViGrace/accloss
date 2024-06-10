package com.clo.accloss.core.modules.offers.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.modules.offers.domain.usecase.GetImages
import com.clo.accloss.core.modules.offers.presentation.state.OffersState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class OffersViewModel(
    getImages: GetImages
) : ScreenModel {
    private var _state = MutableStateFlow(OffersState())
    val state = combine(
        _state,
        getImages()
    ) { state, result ->
        state.copy(
            images = result
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        OffersState()
    )
}
