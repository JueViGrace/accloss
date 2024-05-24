package com.clo.accloss.configuration.domain.mappers

import com.clo.accloss.configuration.data.remote.model.ConfigurationItem
import com.clo.accloss.configuration.domain.model.Configuration
import com.clo.accloss.Configuration as ConfigurationEntity

fun ConfigurationEntity.toDomain(): Configuration = Configuration(
    cnfgActiva = cnfgActiva,
    cnfgClase = cnfgClase,
    cnfgEtiq = cnfgEtiq,
    cnfgIdconfig = cnfgIdconfig,
    cnfgLentxt = cnfgLentxt,
    cnfgTipo = cnfgTipo,
    cnfgTtip = cnfgTtip,
    cnfgValfch = cnfgValfch,
    cnfgValnum = cnfgValnum,
    cnfgValsino = cnfgValsino,
    cnfgValtxt = cnfgValtxt,
    fechamodifi = fechamodifi,
    username = username,
    empresa = empresa,
)

fun ConfigurationItem.toDomain(): Configuration = Configuration(
    cnfgActiva = cnfgActiva ?: 0.0,
    cnfgClase = cnfgClase ?: "",
    cnfgEtiq = cnfgEtiq ?: "",
    cnfgIdconfig = cnfgIdconfig ?: "",
    cnfgLentxt = cnfgLentxt ?: 0.0,
    cnfgTipo = cnfgTipo ?: "",
    cnfgTtip = cnfgTtip ?: "",
    cnfgValfch = cnfgValfch ?: "",
    cnfgValnum = cnfgValnum ?: 0.0,
    cnfgValsino = cnfgValsino ?: 0.0,
    cnfgValtxt = cnfgValtxt ?: "",
    fechamodifi = fechamodifi ?: "",
    username = username ?: "",
    empresa = empresa ?: "",
)

fun Configuration.toDatabase(): ConfigurationEntity = ConfigurationEntity(
    cnfgActiva = cnfgActiva,
    cnfgClase = cnfgClase,
    cnfgEtiq = cnfgEtiq,
    cnfgIdconfig = cnfgIdconfig,
    cnfgLentxt = cnfgLentxt,
    cnfgTipo = cnfgTipo,
    cnfgTtip = cnfgTtip,
    cnfgValfch = cnfgValfch,
    cnfgValnum = cnfgValnum,
    cnfgValsino = cnfgValsino,
    cnfgValtxt = cnfgValtxt,
    fechamodifi = fechamodifi,
    username = username,
    empresa = empresa,
)
