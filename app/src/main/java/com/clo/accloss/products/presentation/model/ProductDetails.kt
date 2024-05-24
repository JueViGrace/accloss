package com.clo.accloss.products.presentation.model

import com.clo.accloss.products.domain.model.Product

data class ProductDetails(
    val product: Product,
    val utilities: Boolean = false
)
