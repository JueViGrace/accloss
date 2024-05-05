package com.clo.accloss.factura.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacturaResponse(

    @SerialName("facturas")
    val facturas: List<FacturaItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class FacturaItem(

    @SerialName("dretencion")
    val dretencion: Double? = null,

    @SerialName("cdret")
    val cdret: Double? = null,

    @SerialName("dtotpagos")
    val dtotpagos: Double? = null,

    @SerialName("dtotdescuen")
    val dtotdescuen: Double? = null,

    @SerialName("kti_negesp")
    val ktiNegesp: String? = null,

    @SerialName("tasadoc")
    val tasadoc: Double? = null,

    @SerialName("documento")
    val documento: String? = null,

    @SerialName("dtotimpuest")
    val dtotimpuest: Double? = null,

    @SerialName("recepcion")
    val recepcion: String? = null,

    @SerialName("bsmtoiva")
    val bsmtoiva: Double? = null,

    @SerialName("retmun_mto")
    val retmunMto: Double? = null,

    @SerialName("cbsrparme")
    val cbsrparme: Double? = null,

    @SerialName("cdrparme")
    val cdrparme: Double? = null,

    @SerialName("agencia")
    val agencia: String? = null,

    @SerialName("codcoord")
    val codcoord: String? = null,

    @SerialName("cdretflete")
    val cdretflete: Double? = null,

    @SerialName("codcliente")
    val codcliente: String? = null,

    @SerialName("bsretencioniva")
    val bsretencioniva: Double? = null,

    @SerialName("tipodocv")
    val tipodocv: String? = null,

    @SerialName("estatusdoc")
    val estatusdoc: String? = null,

    @SerialName("tipodoc")
    val tipodoc: String? = null,

    @SerialName("dretencioniva")
    val dretencioniva: Double? = null,

    @SerialName("fchvencedcto")
    val fchvencedcto: String? = null,

    @SerialName("aceptadev")
    val aceptadev: String? = null,

    @SerialName("ruta_parme")
    val rutaParme: String? = null,

    @SerialName("vendedor")
    val vendedor: String? = null,

    @SerialName("cbsretiva")
    val cbsretiva: Double? = null,

    @SerialName("emision")
    val emision: String? = null,

    @SerialName("cdretiva")
    val cdretiva: Double? = null,

    @SerialName("mtodcto")
    val mtodcto: Double? = null,

    @SerialName("tienedcto")
    val tienedcto: String? = null,

    @SerialName("cbsret")
    val cbsret: Double? = null,

    @SerialName("diascred")
    val diascred: Double? = null,

    @SerialName("dvndmtototal")
    val dvndmtototal: Double? = null,

    @SerialName("cbsretflete")
    val cbsretflete: Double? = null,

    @SerialName("contribesp")
    val contribesp: Double? = null,

    @SerialName("vence")
    val vence: String? = null,

    @SerialName("dtotneto")
    val dtotneto: Double? = null,

    @SerialName("bsflete")
    val bsflete: Double? = null,

    @SerialName("dtotdev")
    val dtotdev: Double? = null,

    @SerialName("nombrecli")
    val nombrecli: String? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("bsmtofte")
    val bsmtofte: Double? = null,

    @SerialName("bsiva")
    val bsiva: Double? = null,

    @SerialName("dFlete")
    val dFlete: Double? = null,

    @SerialName("tipoprecio")
    val tipoprecio: Double? = null,

    @SerialName("dtotalfinal")
    val dtotalfinal: Double? = null
)
