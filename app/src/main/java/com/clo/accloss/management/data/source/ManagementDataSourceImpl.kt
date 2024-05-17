package com.clo.accloss.management.data.source

import com.clo.accloss.management.data.local.ManagementLocal
import com.clo.accloss.management.data.remote.source.ManagementRemote

class ManagementDataSourceImpl(
    override val managementLocal: ManagementLocal,
    override val managementRemote: ManagementRemote
) : ManagementDataSource
