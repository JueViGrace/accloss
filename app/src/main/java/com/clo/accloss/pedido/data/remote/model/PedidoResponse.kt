package com.clo.accloss.pedido.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PedidoResponse(

    @SerialName("pedidos")
    val pedidos: List<PedidoItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class PedidoItem(

    @SerialName("kti_tipprec")
    val ktiTipprec: Double? = null,

    @SerialName("kti_ndoc")
    val ktiNdoc: String? = null,

    @SerialName("ke_pedstatus")
    val kePedstatus: String? = null,

    @SerialName("kti_negesp")
    val ktiNegesp: String? = null,

    @SerialName("kti_nombrecli")
    val ktiNombrecli: String? = null,

    @SerialName("kti_nroped")
    val ktiNroped: String? = null,

    @SerialName("kti_tdoc")
    val ktiTdoc: String? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("kti_docsol")
    val ktiDocsol: String? = null,

    @SerialName("kti_status")
    val ktiStatus: String? = null,

    @SerialName("kti_codcli")
    val ktiCodcli: String? = null,

    @SerialName("kti_codven")
    val ktiCodven: String? = null,

    @SerialName("kti_totneto")
    val ktiTotneto: Double? = null,

    @SerialName("kti_fchdoc")
    val ktiFchdoc: String? = null,

    @SerialName("kti_condicion")
    val ktiCondicion: String? = null
)
