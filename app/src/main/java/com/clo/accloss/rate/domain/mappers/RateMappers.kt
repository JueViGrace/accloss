package com.clo.accloss.rate.domain.mappers

import com.clo.accloss.rate.data.remote.model.RateResponse
import com.clo.accloss.rate.domain.model.Rate
import com.clo.accloss.Tasas as RateEntity

fun RateResponse.toDomain(): Rate = Rate(
    fecha = fecha ?: "",
    fchyhora = fchyhora ?: "",
    fechamodifi = fechamodifi ?: "",
    ip = ip ?: "",
    usuario = usuario ?: "",
    id = id ?: "",
    tasa = tasa ?: 0.0,
    tasaib = tasaib ?: ""
)

fun Rate.toDatabase(): RateEntity = RateEntity(
    fecha = fecha,
    fchyhora = fchyhora,
    fechamodifi = fechamodifi,
    ip = ip,
    usuario = usuario,
    id = id,
    tasa = tasa,
    tasaib = tasaib,
    empresa = empresa
)

fun RateEntity.toDomain(): Rate = Rate(
    fecha = fecha,
    fchyhora = fchyhora,
    fechamodifi = fechamodifi,
    ip = ip,
    usuario = usuario,
    id = id,
    tasa = tasa,
    tasaib = tasaib,
    empresa = empresa
)
