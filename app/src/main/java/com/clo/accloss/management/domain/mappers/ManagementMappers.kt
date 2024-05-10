package com.clo.accloss.management.domain.mappers

import com.clo.accloss.management.data.remote.model.ManagementResponse
import com.clo.accloss.management.domain.model.Management
import com.clo.accloss.Gerencia as ManagementEntity

fun ManagementEntity.toDomain(): Management = Management(
    fechamodifi = fechamodifi,
    kngCodcoord = kngCodcoord,
    kngCodgcia = kngCodgcia,
    empresa = empresa
)

fun Management.toDatabase(): ManagementEntity = ManagementEntity(
    fechamodifi = fechamodifi,
    kngCodcoord = kngCodcoord,
    kngCodgcia = kngCodgcia,
    empresa = empresa
)

fun ManagementResponse.toDomain(): Management = Management(
    fechamodifi = fechamodifi ?: "",
    kngCodcoord = kngCodcoord ?: "",
    kngCodgcia = kngCodgcia ?: ""
)
