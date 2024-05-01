package com.clo.accloss.auth.login.domain.model

data class Login(
    val baseUrl: String? = null,
    val username: String = "",
    val password: String = "",
    val agencia: String = "mcbo"
)
