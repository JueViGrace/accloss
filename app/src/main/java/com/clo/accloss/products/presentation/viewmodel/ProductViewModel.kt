package com.clo.accloss.products.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.products.presentation.state.ProductState
import com.clo.accloss.session.domain.usecase.GetSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getSession: GetSession,
    private val productRepository: ProductRepository
) : ScreenModel {
    private var _state: MutableStateFlow<ProductState> = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        screenModelScope.launch(Dispatchers.IO) {
            getSession.invoke().collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        _state.update { productState ->
                            productState.copy(
                                products = result
                            )
                        }
                    }

                    RequestState.Idle -> {
                        _state.update { productState ->
                            productState.copy(
                                products = RequestState.Idle
                            )
                        }
                    }

                    RequestState.Loading -> {
                        _state.update { productState ->
                            productState.copy(
                                products = RequestState.Loading
                            )
                        }
                    }

                    is RequestState.Success -> {
                        productRepository.getProducts(
                            session = result.data,
                            forceReload = _state.value.reload ?: false
                        ).collect { productResult ->
                            _state.update { productState ->
                                productState.copy(
                                    products = productResult,
                                    reload = false
                                )
                            }
                        }
                    }
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

        getProducts()
    }
}
