package com.clo.accloss.management.data.local

import com.clo.accloss.GetManagementStatistics
import com.clo.accloss.GetManagementsStatistics
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Gerencia as ManagementEntity

interface ManagementLocal {
    suspend fun getManagementsStatistics(
        code: String,
        company: String
    ): Flow<List<GetManagementsStatistics>>

    suspend fun getManagementStatistics(
        code: String,
        company: String
    ): Flow<GetManagementStatistics>

    suspend fun addManagements(managements: List<ManagementEntity>)
}
