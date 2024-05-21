package com.clo.accloss.core.modules.syncronize.presentation.model

import com.clo.accloss.BuildConfig
import com.clo.accloss.core.common.toStringFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class SyncBody(
    @SerialName("sincroonizacion")
    var synchronization: Synchronization
)

@Serializable
data class Synchronization(
    @SerialName("usuario")
    var usuario: String,
    @SerialName("fecha")
    var fecha: String,
    @SerialName("version")
    var version: String,
)

@Serializable
data class Estado(
    @SerialName("estado")
    val response: Response
)

@Serializable
data class Response(
    @SerialName("status")
    val status: Int,
    @SerialName("usuario")
    val user: String
)
