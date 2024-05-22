package com.clo.accloss.products.domain.usecase

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProduct(
    private val getCurrentUser: GetCurrentUser,
    private val productRepository: ProductRepository
) {
    operator fun invoke(id: String): Flow<RequestState<Product>> = flow {
        emit(RequestState.Loading)

        getCurrentUser().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    productRepository.getProduct(
                        company = sessionResult.data.empresa,
                        product = id
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = result.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                emit(
                                    RequestState.Success(
                                        data = result.data
                                    )
                                )
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}