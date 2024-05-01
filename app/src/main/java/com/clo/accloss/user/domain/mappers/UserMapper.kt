package com.clo.accloss.user.domain.mappers

import com.clo.accloss.user.data.remote.model.UserResponse
import com.clo.accloss.user.domain.model.User
import com.clo.accloss.User as UserEntity

fun UserResponse.toDomain(): User = User(
    almacen = almacen ?: "",
    correlativo = correlativo ?: "",
    correlativoreclamo = correlativoreclamo ?: "",
    desactivo = desactivo ?: 0.0,
    fechamodifi = fechamodifi ?: "",
    kcxCodven = kcxCodven ?: "",
    nombre = nombre ?: "",
    password = password ?: "",
    recibocobro = recibocobro ?: "",
    sesionactiva = sesionactiva ?: "",
    superves = superves ?: "",
    ualterprec = ualterprec ?: 0.0,
    username = username ?: "",
    ultSinc = ultSinc ?: "",
    vendedor = vendedor ?: ""
)

fun UserEntity.toDomain(): User = User(
    almacen = almacen,
    correlativo = correlativo,
    correlativoreclamo = correlativoreclamo,
    desactivo = desactivo,
    fechamodifi = fechamodifi,
    kcxCodven = kcxCodven,
    nombre = nombre,
    password = password,
    recibocobro = recibocobro,
    sesionactiva = sesionactiva,
    superves = superves,
    ualterprec = ualterprec,
    username = username,
    ultSinc = ultSinc,
    empresa = empresa,
    vendedor = vendedor
)

fun User.toDatabase(): UserEntity = UserEntity(
    almacen = almacen,
    correlativo = correlativo,
    correlativoreclamo = correlativoreclamo,
    desactivo = desactivo,
    fechamodifi = fechamodifi,
    kcxCodven = kcxCodven,
    nombre = nombre,
    password = password,
    recibocobro = recibocobro,
    sesionactiva = sesionactiva,
    superves = superves,
    ualterprec = ualterprec,
    username = username,
    ultSinc = ultSinc,
    empresa = empresa,
    vendedor = vendedor
)
