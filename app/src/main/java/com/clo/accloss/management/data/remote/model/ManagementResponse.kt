package com.clo.accloss.management.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagementResponse(
    @SerialName("kng_codgcia")
    val kngCodgcia: String? = null,

    @SerialName("kng_codcoord")
    val kngCodcoord: String? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null
)
