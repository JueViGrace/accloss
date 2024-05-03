package com.clo.accloss.lineaspedido.domain.model

data class LineasPedido(
    val kmvArtprec: Double = 0.0,
    val kmvCant: Double = 0.0,
    val kmvCodart: String = "",
    val kmvDctolin: Double = 0.0,
    val kmvNombre: String = "",
    val kmvStot: Double = 0.0,
    val ktiNdoc: String = "",
    val ktiTdoc: String = "",
    val ktiTipprec: Double = 0.0,
    val empresa: String = "",
)
