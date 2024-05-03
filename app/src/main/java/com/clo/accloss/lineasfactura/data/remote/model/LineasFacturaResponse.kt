package com.clo.accloss.lineasfactura.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineasFacturaResponse(

    @SerialName("datos_facturas")
    val datosFacturas: List<LineasFacturasItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class LineasFacturasItem(

    @SerialName("dpreciofin")
    val dpreciofin: Double? = null,

    @SerialName("grupo")
    val grupo: String? = null,

    @SerialName("documento")
    val documento: String? = null,

    @SerialName("pid")
    val pid: String? = null,

    @SerialName("origen")
    val origen: Double? = null,

    @SerialName("nombre")
    val nombre: String? = null,

    @SerialName("agencia")
    val agencia: String? = null,

    @SerialName("codcoord")
    val codcoord: String? = null,

    @SerialName("tipodocv")
    val tipodocv: String? = null,

    @SerialName("subgrupo")
    val subgrupo: String? = null,

    @SerialName("tipodoc")
    val tipodoc: String? = null,

    @SerialName("codhijo")
    val codhijo: String? = null,

    @SerialName("dmontototal")
    val dmontototal: Double? = null,

    @SerialName("timpueprc")
    val timpueprc: Double? = null,

    @SerialName("codigo")
    val codigo: String? = null,

    @SerialName("vendedor")
    val vendedor: String? = null,

    @SerialName("dvndmtototal")
    val dvndmtototal: Double? = null,

    @SerialName("unidevuelt")
    val unidevuelt: Double? = null,

    @SerialName("cntdevuelt")
    val cntdevuelt: Double? = null,

    @SerialName("dmontoneto")
    val dmontoneto: Double? = null,

    @SerialName("vndcntdevuelt")
    val vndcntdevuelt: Double? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("cantidad")
    val cantidad: Double? = null,

    @SerialName("dpreciounit")
    val dpreciounit: Double? = null,

    @SerialName("fechadoc")
    val fechadoc: String? = null
)
