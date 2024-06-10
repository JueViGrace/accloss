package com.clo.accloss.core.modules.offers.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("imgs")
    val imgs: List<ImgsItem> = emptyList(),
    @SerialName("status")
    val status: String? = null
)

@Serializable
data class ImgsItem(
    @SerialName("fechamodifi")
    val fechamodifi: String? = null,
    @SerialName("enlace")
    val enlace: String? = null,
    @SerialName("nombre")
    val nombre: String? = null,
)
