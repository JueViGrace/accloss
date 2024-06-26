package com.clo.accloss.products.presentation.state

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.domain.model.Product

data class ProductState(
    val products: RequestState<List<Product>> = RequestState.Loading,
    val reload: Boolean = false,
    val searchBarVisible: Boolean = false,
    val searchText: String = ""
)
