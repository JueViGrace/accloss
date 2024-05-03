package com.clo.accloss.vendedor.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VendedorResponse(
    @SerialName("supervpor")
    val supervpor: String? = null,

    @SerialName("ult_sinc")
    val ultSinc: String? = null,

    @SerialName("vendedor")
    val vendedor: String? = null,

    @SerialName("subsector")
    val subsector: String? = null,

    @SerialName("telefonos")
    val telefonos: String? = null,

    @SerialName("nombre")
    val nombre: String? = null,

    @SerialName("sector")
    val sector: String? = null,

    @SerialName("version")
    val version: String? = null,

    @SerialName("telefono_movil")
    val telefonoMovil: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("username")
    val username: String? = null
)
