package com.clo.accloss.management.data.source

import com.clo.accloss.management.data.local.ManagementLocal
import com.clo.accloss.management.data.remote.source.ManagementRemote

interface ManagementDataSource {
    val managementLocal: ManagementLocal
    val managementRemote: ManagementRemote
}