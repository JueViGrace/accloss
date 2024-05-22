package com.clo.accloss.products.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.products.domain.model.Product

data class ProductDetailState(
    val product: RequestState<Product> = RequestState.Loading,
)
