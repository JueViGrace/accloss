package com.clo.accloss.user.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("almacen")
    val almacen: String? = null,
    @SerialName("correlativo")
    val correlativo: String? = null,
    @SerialName("correlativoreclamo")
    val correlativoreclamo: String? = null,
    @SerialName("desactivo")
    val desactivo: Double? = null,
    @SerialName("fechamodifi")
    val fechamodifi: String? = null,
    @SerialName("kcxCodven")
    val kcxCodven: String? = null,
    @SerialName("nombre")
    val nombre: String? = null,
    @SerialName("password")
    val password: String? = null,
    @SerialName("recibocobro")
    val recibocobro: String? = null,
    @SerialName("sesionactiva")
    val sesionactiva: String? = null,
    @SerialName("superves")
    val superves: String? = null,
    @SerialName("ualterprec")
    val ualterprec: Double? = null,
    @SerialName("username")
    val username: String? = null,
    @SerialName("ultSinc")
    val ultSinc: String? = null,
    @SerialName("vendedor")
    val vendedor: String? = null,
)

