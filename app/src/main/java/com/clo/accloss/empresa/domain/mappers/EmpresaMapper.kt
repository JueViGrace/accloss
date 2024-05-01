package com.clo.accloss.empresa.domain.mappers

import com.clo.accloss.empresa.data.remote.model.EmpresaResponse
import com.clo.accloss.empresa.domain.model.Empresa
import com.clo.accloss.Empresa as EmpresaEntity

fun EmpresaResponse.toDomain(): Empresa = Empresa(
    agenciaEmpresa = agenciaEmpresa ?: "",
    nombreEmpresa = nombreEmpresa ?: "",
    codigoEmpresa = codigoEmpresa ?: "",
    statusEmpresa = statusEmpresa ?: "",
    enlaceEmpresa = enlaceEmpresa ?: "",
    enlaceEmpresaPost = enlaceEmpresaPost ?: ""
)

fun EmpresaEntity.toDomain(): Empresa = Empresa(
    agenciaEmpresa = agenciaEmpresa,
    nombreEmpresa = nombreEmpresa,
    codigoEmpresa = codigoEmpresa,
    statusEmpresa = statusEmpresa,
    enlaceEmpresa = enlaceEmpresa,
    enlaceEmpresaPost = enlaceEmpresaPost
)

fun Empresa.toDatabase(): EmpresaEntity = EmpresaEntity(
    agenciaEmpresa = agenciaEmpresa,
    nombreEmpresa = nombreEmpresa,
    codigoEmpresa = codigoEmpresa,
    statusEmpresa = statusEmpresa,
    enlaceEmpresa = enlaceEmpresa,
    enlaceEmpresaPost = enlaceEmpresaPost
)
