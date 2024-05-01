package com.clo.accloss.user.domain.model

data class User(
    val almacen: String = "",
    val correlativo: String = "",
    val correlativoreclamo: String = "",
    val desactivo: Double = 0.0,
    val fechamodifi: String = "",
    val kcxCodven: String = "",
    val nombre: String = "",
    val password: String = "",
    val recibocobro: String = "",
    val sesionactiva: String = "",
    val superves: String = "",
    val ualterprec: Double = 0.0,
    val username: String = "",
    val ultSinc: String = "0001-01-01 01:01:01",
    val empresa: String = "",
    val vendedor: String = "",
)
