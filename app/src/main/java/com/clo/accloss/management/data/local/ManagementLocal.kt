package com.clo.accloss.management.data.local

import kotlinx.coroutines.CoroutineScope
import com.clo.accloss.Gerencia as ManagementEntity

interface ManagementLocal {
    val scope: CoroutineScope

    suspend fun addManagements(managements: List<ManagementEntity>)

    suspend fun deleteManagements(company: String)
}
