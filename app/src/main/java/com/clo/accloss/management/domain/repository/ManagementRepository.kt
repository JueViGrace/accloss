package com.clo.accloss.management.domain.repository

import com.clo.accloss.core.state.RequestState
import com.clo.accloss.management.data.source.ManagementDataSource
import com.clo.accloss.management.domain.model.Management

interface ManagementRepository {
    val managementDataSource: ManagementDataSource

    suspend fun getRemoteManagements(
        baseUrl: String,
        user: String,
        company: String
    ): RequestState<List<Management>>

    suspend fun addManagements(managements: List<Management>)
}
