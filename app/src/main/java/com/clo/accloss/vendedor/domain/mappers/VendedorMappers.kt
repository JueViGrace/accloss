package com.clo.accloss.vendedor.domain.mappers

import com.clo.accloss.vendedor.data.remote.model.VendedorResponse
import com.clo.accloss.vendedor.domain.model.Vendedor
import com.clo.accloss.Vendedor as VendedorEntity

fun VendedorEntity.toDomain(): Vendedor = Vendedor(
    email = email,
    nombre = nombre,
    sector = sector,
    subsector = subsector,
    supervpor = supervpor,
    telefonoMovil = telefonoMovil,
    telefonos = telefonos,
    ultSinc = ultSinc,
    username = username,
    vendedor = vendedor,
    version = version,
    empresa = empresa
)

fun Vendedor.toDatabase(): VendedorEntity = VendedorEntity(
    email = email,
    nombre = nombre,
    sector = sector,
    subsector = subsector,
    supervpor = supervpor,
    telefonoMovil = telefonoMovil,
    telefonos = telefonos,
    ultSinc = ultSinc,
    username = username,
    vendedor = vendedor,
    version = version,
    empresa = empresa
)

fun VendedorResponse.toDomain(): Vendedor = Vendedor(
    email = email ?: "",
    nombre = nombre ?: "",
    sector = sector ?: "",
    subsector = subsector ?: "",
    supervpor = supervpor ?: "",
    telefonoMovil = telefonoMovil ?: "",
    telefonos = telefonos ?: "",
    ultSinc = ultSinc ?: "",
    username = username ?: "",
    vendedor = vendedor ?: "",
    version = version ?: ""
)
