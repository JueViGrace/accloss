package com.clo.accloss.products.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.products.domain.usecase.GetProducts
import com.clo.accloss.products.presentation.state.ProductState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProducts: GetProducts,
) : ScreenModel {
    private var _state: MutableStateFlow<ProductState> = MutableStateFlow(ProductState())
    val state = combine(
        _state,
        getProducts()
    ) { state, result ->
        state.copy(
            products = result
        )
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ProductState()
        )

    private suspend fun updateProducts() {
        getProducts(_state.value.reload == true).collect { result ->
            _state.update { productState ->
                productState.copy(
                    products = result,
                    reload = null
                )
            }
        }
    }

    fun onRefresh() {
        screenModelScope.launch {
            _state.update { productState ->
                productState.copy(
                    reload = true
                )
            }
            delay(500)
            updateProducts()
        }
    }
}
