package com.clo.accloss.products.presentation.viewmodel

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.common.Constants.SHARING_STARTED
import com.clo.accloss.core.state.RequestState
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
        when (result) {
            is RequestState.Success -> {
                if (state.searchText.isBlank()) {
                    state.copy(
                        products = result,
                    )
                } else {
                    val data = result.data.filter { product ->
                        product.nombre.lowercase().contains(state.searchText.trim().lowercase()) ||
                            product.codigo.lowercase().contains(state.searchText.trim().lowercase()) ||
                            product.referencia.lowercase().contains(state.searchText.trim().lowercase())
                    }

                    state.copy(
                        products = RequestState.Success(data),
                    )
                }
            }
            else -> {
                state.copy(
                    products = result,
                )
            }
        }
    }.stateIn(
        screenModelScope,
        SHARING_STARTED,
        ProductState()
    )

    fun onSearchTextChange(text: String) {
        _state.update { productState ->
            productState.copy(
                searchText = text
            )
        }
    }

    fun toggleVisibility(force: Boolean? = null) {
        _state.update { productState ->
            productState.copy(
                searchBarVisible = force ?: productState.searchBarVisible
            )
        }
    }

    private fun updateProducts() {
        screenModelScope.launch(Dispatchers.IO) {
            getProducts(_state.value.reload).collect { result ->
                Log.i("UPDATE PRODUCTS", "updateProducts: $result")
                _state.update { productState ->
                    productState.copy(
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
