package com.clo.accloss.management.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.domain.model.Management
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import kotlinx.coroutines.flow.Flow

interface ManagementRepository {
    val managementDataSource: ManagementDataSource

    suspend fun getRemoteManagements(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Management>>

    fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<RequestState<List<PersonalStatistics>>>

    suspend fun addManagements(managements: List<Management>)
}
