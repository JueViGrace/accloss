package com.clo.accloss.session.domain.model

data class Session(
    val user: String = "",
    val empresa: String = "",
    val enlaceEmpresa: String = "",
    val enlaceEmpresaPost: String = "",
    val active: Boolean = true
)
