package com.clo.accloss.products.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.products.domain.usecase.GetProducts
import com.clo.accloss.products.presentation.state.ProductState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProducts: GetProducts,
) : ScreenModel {
    private var _state: MutableStateFlow<ProductState> = MutableStateFlow(ProductState())
    val state = combine(
        _state,
        getProducts(),
    ) { state, result ->
        state.copy(
            products = result
        )
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ProductState()
    )

    // TODO: CHECK FOR RELOAD

    private fun updateProducts() {
        screenModelScope.launch(Dispatchers.IO) {
            getProducts(_state.value.reload).collect { result ->
                _state.update { productState ->
                    productState.copy(
                        products = result,
                        reload = false
                    )
                }
            }
        }
    }

    fun onRefresh() {
        _state.update { productState ->
            productState.copy(
                reload = true
            )
        }

        updateProducts()
    }
}
