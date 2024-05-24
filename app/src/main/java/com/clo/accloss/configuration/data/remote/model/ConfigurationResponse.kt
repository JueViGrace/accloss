package com.clo.accloss.configuration.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(

    @SerialName("config")
    val config: List<ConfigurationItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class ConfigurationItem(

    @SerialName("cnfg_valsino")
    val cnfgValsino: Double? = null,

    @SerialName("cnfg_activa")
    val cnfgActiva: Double? = null,

    @SerialName("cnfg_valnum")
    val cnfgValnum: Double? = null,

    @SerialName("cnfg_idconfig")
    val cnfgIdconfig: String? = null,

    @SerialName("cnfg_etiq")
    val cnfgEtiq: String? = null,

    @SerialName("cnfg_tipo")
    val cnfgTipo: String? = null,

    @SerialName("cnfg_valfch")
    val cnfgValfch: String? = null,

    @SerialName("cnfg_valtxt")
    val cnfgValtxt: String? = null,

    @SerialName("cnfg_ttip")
    val cnfgTtip: String? = null,

    @SerialName("cnfg_lentxt")
    val cnfgLentxt: Double? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("empresa")
    val empresa: String? = null,

    @SerialName("cnfg_clase")
    val cnfgClase: String? = null,

    @SerialName("username")
    val username: String? = null
)
