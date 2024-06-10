package com.clo.accloss.products.domain.usecase

import com.clo.accloss.configuration.domain.repository.ConfigurationRepository
import com.clo.accloss.core.common.Constants.APPC_UTILIDADES_KEY
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.products.presentation.model.ProductDetails
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProduct(
    private val getCurrentUser: GetCurrentUser,
    private val productRepository: ProductRepository,
    private val configurationRepository: ConfigurationRepository
) {
    operator fun invoke(id: String): Flow<RequestState<ProductDetails>> = flow {
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
                                val config = configurationRepository.getConfigBool(
                                    key = APPC_UTILIDADES_KEY,
                                    company = sessionResult.data.empresa
                                )

                                val data = ProductDetails(
                                    product = result.data,
                                )

                                when (config) {
                                    is RequestState.Error -> {
                                        emit(
                                            RequestState.Success(
                                                data = data
                                            )
                                        )
                                    }
                                    is RequestState.Success -> {
                                        emit(
                                            RequestState.Success(
                                                data = data.copy(
                                                    utilities = config.data
                                                )
                                            )
                                        )
                                    }
                                    else -> emit(RequestState.Loading)
                                }
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