package com.clo.accloss.core.modules.offers.presentation.state

import com.clo.accloss.core.modules.offers.domain.model.Image
import com.clo.accloss.core.state.RequestState

data class OffersState(
    val images: RequestState<List<Image>> = RequestState.Loading
)
