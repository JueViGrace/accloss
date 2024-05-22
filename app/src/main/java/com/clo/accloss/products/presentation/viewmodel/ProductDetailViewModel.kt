package com.clo.accloss.products.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.products.domain.usecase.GetProduct
import com.clo.accloss.products.presentation.state.ProductDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProductDetailViewModel(
    private val getProduct: GetProduct,
    private val id: String
) : ScreenModel {
    private var _state: MutableStateFlow<ProductDetailState> = MutableStateFlow(ProductDetailState())
    val state = combine(
        _state,
        getProduct(id)
    ) { state, product ->
        state.copy(
            product = product
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ProductDetailState()
    )
}
