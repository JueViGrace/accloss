package com.clo.accloss.salesman.domain.usecase

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSalesman(
    private val getCurrentUser: GetCurrentUser,
    private val salesmanRepository: SalesmanRepository
) {
    operator fun invoke(id: String): Flow<RequestState<Salesman>> = flow {
        emit(RequestState.Loading)

        getCurrentUser().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(RequestState.Error(message = sessionResult.message))
                }
                is RequestState.Success -> {
                    salesmanRepository.getSalesman(
                        salesman = id,
                        company = sessionResult.data.empresa
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
