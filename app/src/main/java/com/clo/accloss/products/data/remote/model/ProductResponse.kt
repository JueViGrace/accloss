package com.clo.accloss.products.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(

    @SerialName("articulo")
    val product: List<ProductResponseItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class ProductResponseItem(

    @SerialName("enpreventa")
    val enpreventa: String? = null,

    @SerialName("vta_solofac")
    val vtaSolofac: Int? = null,

    @SerialName("grupo")
    val grupo: String? = null,

    @SerialName("nombre")
    val nombre: String? = null,

    @SerialName("marca")
    val marca: String? = null,

    @SerialName("existencia")
    val existencia: Double? = null,

    @SerialName("subgrupo")
    val subgrupo: String? = null,

    @SerialName("comprometido")
    val comprometido: Double? = null,

    @SerialName("vta_solone")
    val vtaSolone: Int? = null,

    @SerialName("vta_max")
    val vtaMax: Double? = null,

    @SerialName("discont")
    val discont: Double? = null,

    @SerialName("vta_minenx")
    val vtaMinenx: Double? = null,

    @SerialName("codigo")
    val codigo: String? = null,

    @SerialName("vta_min")
    val vtaMin: Double? = null,

    @SerialName("dctotope")
    val dctotope: Double? = null,

    @SerialName("precio1")
    val precio1: Double? = null,

    @SerialName("precio2")
    val precio2: Double? = null,

    @SerialName("precio3")
    val precio3: Double? = null,

    @SerialName("precio4")
    val precio4: Double? = null,

    @SerialName("precio5")
    val precio5: Double? = null,

    @SerialName("precio6")
    val precio6: Double? = null,

    @SerialName("unidad")
    val unidad: String? = null,

    @SerialName("precio7")
    val precio7: Double? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("referencia")
    val referencia: String? = null,

    @SerialName("costo_prom")
    val costoProm: Double? = null,

    @SerialName("util1")
    val util1: Double? = null,

    @SerialName("util2")
    val util2: Double? = null,

    @SerialName("util3")
    val util3: Double? = null,

    @SerialName("fchultcomp")
    val fchUltComp: String? = null,

    @SerialName("qtyultcomp")
    val qtyUltComp: Double? = null,
)
