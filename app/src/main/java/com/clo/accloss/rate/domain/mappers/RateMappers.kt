package com.clo.accloss.rate.domain.mappers

import com.clo.accloss.rate.data.remote.model.RateResponse
import com.clo.accloss.rate.domain.model.Rate

fun RateResponse.toDomain(): Rate = Rate(
    fecha = fecha ?: "",
    fchyhora = fchyhora ?: "",
    fechamodifi = fechamodifi ?: "",
    ip = ip ?: "",
    usuario = usuario ?: "",
    id = id ?: "",
    tasa = tasa ?: 0.0
)
