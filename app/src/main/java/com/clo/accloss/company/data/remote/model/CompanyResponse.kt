package com.clo.accloss.company.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse(
    @SerialName("agenciaEmpresa")
    val agenciaEmpresa: String? = null,
    @SerialName("nombreEmpresa")
    val nombreEmpresa: String? = null,
    @SerialName("codigoEmpresa")
    val codigoEmpresa: String? = null,
    @SerialName("statusEmpresa")
    val statusEmpresa: String? = null,
    @SerialName("enlaceEmpresa")
    val enlaceEmpresa: String? = null,
    @SerialName("enlaceEmpresaPost")
    val enlaceEmpresaPost: String? = null
)
