package com.clo.accloss.products.presentation.state

import com.clo.accloss.products.domain.model.Product

data class ProductState(
    val products: List<Product> = emptyList(),
    val reload: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
