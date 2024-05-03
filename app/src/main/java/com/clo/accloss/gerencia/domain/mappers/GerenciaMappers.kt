package com.clo.accloss.gerencia.domain.mappers

import com.clo.accloss.gerencia.data.remote.model.GerenciaResponse
import com.clo.accloss.gerencia.domain.model.Gerencia
import com.clo.accloss.Gerencia as GerenciaEntity

fun GerenciaEntity.toDomain(): Gerencia = Gerencia(
    fechamodifi = fechamodifi,
    kngCodcoord = kngCodcoord,
    kngCodgcia = kngCodgcia,
    empresa = empresa
)

fun Gerencia.toDatabase(): GerenciaEntity = GerenciaEntity(
    fechamodifi = fechamodifi,
    kngCodcoord = kngCodcoord,
    kngCodgcia = kngCodgcia,
    empresa = empresa
)

fun GerenciaResponse.toDomain(): Gerencia = Gerencia(
    fechamodifi = fechamodifi ?: "",
    kngCodcoord = kngCodcoord ?: "",
    kngCodgcia = kngCodgcia ?: ""
)
