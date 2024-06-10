package com.clo.accloss.management.data.repository

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.domain.mappers.toDatabase
import com.clo.accloss.management.domain.mappers.toDomain
import com.clo.accloss.management.domain.model.Management
import com.clo.accloss.management.domain.repository.ManagementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManagementRepositoryImpl(
    override val managementDataSource: ManagementDataSource
) : ManagementRepository {
    override suspend fun getRemoteManagements(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Management>> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = managementDataSource.managementRemote
                    .getSafeManagements(
                        baseUrl = baseUrl,
                        user = user
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val list = apiOperation.data.map { managementResponse ->
                        managementResponse.toDomain().copy(empresa = company)
                    }

                    addManagements(list)

                    RequestState.Success(
                        data = list
                    )
                }
            }
        }
    }

    override suspend fun addManagements(managements: List<Management>) =
        withContext(Dispatchers.IO) {
            managementDataSource.managementLocal.addManagements(
                managements = managements.map { management ->
                    management.toDatabase()
                }
            )
        }
}
