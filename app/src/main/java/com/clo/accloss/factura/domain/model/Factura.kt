package com.clo.accloss.factura.domain.model

data class Factura(
    val aceptadev: String = "",
    val agencia: String = "",
    val bsflete: Double = 0.0,
    val bsiva: Double = 0.0,
    val bsmtofte: Double = 0.0,
    val bsmtoiva: Double = 0.0,
    val bsretencioniva: Double = 0.0,
    val cbsret: Double = 0.0,
    val cbsretflete: Double = 0.0,
    val cbsretiva: Double = 0.0,
    val cbsrparme: Double = 0.0,
    val cdret: Double = 0.0,
    val cdretflete: Double = 0.0,
    val cdretiva: Double = 0.0,
    val cdrparme: Double = 0.0,
    val codcliente: String = "",
    val codcoord: String = "",
    val contribesp: Double = 0.0,
    val dFlete: Double = 0.0,
    val diascred: Double = 0.0,
    val documento: String = "",
    val dretencion: Double = 0.0,
    val dretencioniva: Double = 0.0,
    val dtotalfinal: Double = 0.0,
    val dtotdescuen: Double = 0.0,
    val dtotdev: Double = 0.0,
    val dtotimpuest: Double = 0.0,
    val dtotneto: Double = 0.0,
    val dtotpagos: Double = 0.0,
    val dvndmtototal: Double = 0.0,
    val emision: String = "",
    val estatusdoc: String = "",
    val fchvencedcto: String = "",
    val fechamodifi: String = "",
    val ktiNegesp: String = "",
    val mtodcto: Double = 0.0,
    val nombrecli: String = "",
    val recepcion: String = "",
    val retmunMto: Double = 0.0,
    val rutaParme: String = "",
    val tasadoc: Double = 0.0,
    val tienedcto: String = "",
    val tipodoc: String = "",
    val tipodocv: String = "",
    val tipoprecio: Double = 0.0,
    val vence: String = "",
    val vendedor: String = "",
    val empresa: String = ""
)
