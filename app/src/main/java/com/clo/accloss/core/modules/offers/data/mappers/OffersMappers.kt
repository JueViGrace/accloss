package com.clo.accloss.core.modules.offers.data.mappers

import com.clo.accloss.Imagen
import com.clo.accloss.core.modules.offers.data.remote.model.ImgsItem
import com.clo.accloss.core.modules.offers.domain.model.Image

fun ImgsItem.toDomain(): Image = Image(
    fechamodifi = fechamodifi ?: "",
    enlace = enlace ?: "",
    nombre = nombre ?: "",
)

fun Imagen.toDomain(): Image = Image(
    fechamodifi = fechamodifi,
    enlace = enlace,
    nombre = nombre,
    empresa = empresa
)

fun Image.toDatabase(): Imagen = Imagen(
    fechamodifi = fechamodifi,
    enlace = enlace,
    nombre = nombre,
    empresa = empresa
)
