package com.clo.accloss.lineaspedido.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineasPedidosResponse(

    @SerialName("datos_pedidos")
    val datosPedidos: List<LineasPedidosItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class LineasPedidosItem(

    @SerialName("kmv_nombre")
    val kmvNombre: String? = null,

    @SerialName("kmv_stot")
    val kmvStot: Double? = null,

    @SerialName("kmv_dctolin")
    val kmvDctolin: Double? = null,

    @SerialName("kti_tipprec")
    val ktiTipprec: Double? = null,

    @SerialName("kti_ndoc")
    val ktiNdoc: String? = null,

    @SerialName("kmv_cant")
    val kmvCant: Double? = null,

    @SerialName("kmv_artprec")
    val kmvArtprec: Double? = null,

    @SerialName("kti_tdoc")
    val ktiTdoc: String? = null,

    @SerialName("kmv_codart")
    val kmvCodart: String? = null
)
