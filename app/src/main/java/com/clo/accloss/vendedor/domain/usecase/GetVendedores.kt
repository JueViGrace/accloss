package com.clo.accloss.vendedor.domain.usecase

import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.usecase.GetSession
import com.clo.accloss.vendedor.domain.model.Vendedor
import com.clo.accloss.vendedor.domain.repository.VendedorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetVendedores(
    private val getSession: GetSession,
    private val vendedorRepository: VendedorRepository
) {
    operator fun invoke(
        forceReload: Boolean = false
    ): Flow<RequestState<List<Vendedor>>> = flow {
        emit(RequestState.Loading)

        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(RequestState.Error(sessionResult.message))
                }
                is RequestState.Success -> {
                    vendedorRepository.getVendedores(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        empresa = sessionResult.data.empresa,
                        forceReload = forceReload
                    )
                        .catch { e ->
                            emit(RequestState.Error(message = e.message ?: "Get vendedores went wrong"))
                        }
                        .collect { result ->
                            when (result) {
                                is RequestState.Error -> {
                                    emit(RequestState.Error(result.message))
                                }
                                is RequestState.Success -> {
                                    emit(RequestState.Success(data = result.data))
                                }
                                else -> {
                                    emit(RequestState.Loading)
                                }
                            }
                        }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}
