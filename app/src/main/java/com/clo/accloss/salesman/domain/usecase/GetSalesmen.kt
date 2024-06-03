package com.clo.accloss.salesman.domain.usecase

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.session.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSalesmen(
    private val getSession: GetCurrentUser,
    private val salesmanRepository: SalesmanRepository
) {
    operator fun invoke(): Flow<RequestState<List<Salesman>>> = flow {
        emit(RequestState.Loading)

        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(RequestState.Error(sessionResult.message))
                }
                is RequestState.Success -> {
                    salesmanRepository.getSalesmen(
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(RequestState.Error(result.message))
                            }
                            is RequestState.Success -> {
                                emit(RequestState.Success(data = result.data))
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
