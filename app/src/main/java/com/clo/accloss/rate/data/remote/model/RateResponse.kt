package com.clo.accloss.rate.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RateResponse(
    @SerialName("fecha")
    val fecha: String? = null,

    @SerialName("fchyhora")
    val fchyhora: String? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("ip")
    val ip: String? = null,

    @SerialName("usuario")
    val usuario: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("tasa")
    val tasa: Double? = null,

    @SerialName("tasaib")
    val tasaib: String? = null
)
