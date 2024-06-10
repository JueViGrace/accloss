package com.clo.accloss.products.domain.usecase

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProducts(
    private val getSession: GetCurrentUser,
    private val productRepository: ProductRepository
) {
    operator fun invoke(
        forceReload: Boolean = false
    ): Flow<RequestState<List<Product>>> = flow {
        var reload = forceReload

        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(RequestState.Error(sessionResult.message))
                }
                is RequestState.Success -> {
                    productRepository.getProducts(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        company = sessionResult.data.empresa,
                        forceReload = forceReload
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(RequestState.Error(result.message))
                            }
                            is RequestState.Success -> {
                                if (result.data.isNotEmpty() && !reload) {
                                    emit(RequestState.Success(data = result.data))
                                } else {
                                    val apiResult = productRepository.getRemoteProducts(
                                        baseUrl = sessionResult.data.enlaceEmpresa,
                                        company = sessionResult.data.empresa,
                                        lastSync = sessionResult.data.lastSync
                                    )

                                    when (apiResult) {
                                        is RequestState.Error -> {
                                            emit(RequestState.Error(message = apiResult.message))
                                        }
                                        is RequestState.Success -> {
                                            reload = false
                                        }
                                        else -> emit(RequestState.Loading)
                                    }
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
