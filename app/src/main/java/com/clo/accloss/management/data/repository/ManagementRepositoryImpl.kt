package com.clo.accloss.management.data.repository

import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.domain.mappers.toDatabase
import com.clo.accloss.management.domain.mappers.toDomain
import com.clo.accloss.management.domain.model.Management
import com.clo.accloss.management.domain.repository.ManagementRepository
import com.clo.accloss.management.presentation.model.ManagementsUi
import com.clo.accloss.statistic.domain.mappers.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    override fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<RequestState<List<ManagementsUi>>> = flow {
        emit(RequestState.Loading)

        managementDataSource.managementLocal
            .getManagementsStatistics(
                code = code,
                company = company
            )
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("MANAGEMENT REPOSITORY: getManagementsStatistics")
            }
            .collect { list ->
                emit(
                    RequestState.Success(
                        data = list.map { getManagementsStatistics ->
                            getManagementsStatistics.toUi()
                        }
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override fun getManagementStatistics(
        code: String,
        company: String,
    ): Flow<RequestState<ManagementsUi>> = flow {
        emit(RequestState.Loading)

        managementDataSource.managementLocal
            .getManagementStatistics(
                code = code,
                company = company
            )
            .catch { e ->
                emit(RequestState.Error(message = DB_ERROR_MESSAGE))
                e.log("MANAGEMENT REPOSITORY: getManagementsStatistics")
            }
            .collect { getManagementStatistics ->
                emit(
                    RequestState.Success(
                        data = getManagementStatistics.toUi()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    override suspend fun addManagements(managements: List<Management>) =
        withContext(Dispatchers.IO) {
            managementDataSource.managementLocal.addManagements(
                managements = managements.map { management ->
                    management.toDatabase()
                }
            )
        }
}
