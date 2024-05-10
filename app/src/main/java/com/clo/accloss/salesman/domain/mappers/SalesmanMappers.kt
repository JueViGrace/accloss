package com.clo.accloss.salesman.domain.mappers

import com.clo.accloss.salesman.data.remote.model.SalesmanResponse
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.Vendedor as SalesmanEntity

fun SalesmanEntity.toDomain(): Salesman = Salesman(
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

fun Salesman.toDatabase(): SalesmanEntity = SalesmanEntity(
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

fun SalesmanResponse.toDomain(): Salesman = Salesman(
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
