package com.clo.accloss.management.domain.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.management.data.local.ManagementLocalSource
import com.clo.accloss.management.data.remote.source.ManagementRemoteSource
import com.clo.accloss.management.domain.mappers.toDatabase
import com.clo.accloss.management.domain.mappers.toDomain
import com.clo.accloss.management.domain.model.Management
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ManagementRepository(
    private val managementRemoteSource: ManagementRemoteSource,
    private val managementLocalSource: ManagementLocalSource
) {
    fun getRemoteGerencia(
        baseUrl: String,
        user: String,
        company: String
    ): Flow<RequestState<List<Management>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = managementRemoteSource
            .getSafeManagements(
                baseUrl = baseUrl,
                user = user
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error
                    )
                )
            }
            is ApiOperation.Success -> {
                val list = apiOperation.data.map { managementResponse ->
                    managementResponse.toDomain().copy(empresa = company)
                }
                addManagements(list)
                emit(
                    RequestState.Success(
                        data = list
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun addManagements(managements: List<Management>) =
        managementLocalSource.addManagements(
            managements = managements.map { management ->
                management.toDatabase()
            }
        )
}
