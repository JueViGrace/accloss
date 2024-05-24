package com.clo.accloss.products.presentation.state

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.products.presentation.model.ProductDetails

data class ProductDetailState(
    val product: RequestState<ProductDetails> = RequestState.Loading,
)
