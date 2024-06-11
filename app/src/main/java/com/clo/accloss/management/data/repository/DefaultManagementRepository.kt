package com.clo.accloss.management.data.repository

import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.domain.mappers.toDatabase
import com.clo.accloss.management.domain.mappers.toDomain
import com.clo.accloss.management.domain.model.Management
import com.clo.accloss.management.domain.repository.ManagementRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultManagementRepository(
    override val managementDataSource: ManagementDataSource,
    override val coroutineContext: CoroutineContext,
) : ManagementRepository {
    override suspend fun getRemoteManagements(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Management>> {
        return withContext(coroutineContext) {
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

    override suspend fun addManagements(managements: List<Management>) {
        withContext(coroutineContext) {
            managementDataSource.managementLocal.addManagements(
                managements = managements.map { management ->
                    management.toDatabase()
                }
            )
        }
    }

    override suspend fun deleteManagements(company: String) {
        withContext(coroutineContext) {
            managementDataSource.managementLocal.deleteManagements(company = company)
        }
    }
}
