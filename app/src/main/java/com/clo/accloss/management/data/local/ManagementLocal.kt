package com.clo.accloss.management.data.local

import com.clo.accloss.Gerencia as ManagementEntity

interface ManagementLocal {
    suspend fun addManagements(managements: List<ManagementEntity>)
}
