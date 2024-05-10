package com.clo.accloss.statistic.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticResponse(
    @SerialName("cntclientes")
    val cntclientes: Double? = null,

    @SerialName("ppgdol_totneto")
    val ppgdolTotneto: Double? = null,

    @SerialName("totdolcob")
    val totdolcob: Double? = null,

    @SerialName("mtopedidos")
    val mtopedidos: Double? = null,

    @SerialName("codcoord")
    val codcoord: String? = null,

    @SerialName("prcmeta")
    val prcmeta: Double? = null,

    @SerialName("mtorecl")
    val mtorecl: Double? = null,

    @SerialName("fecha_estad")
    val fechaEstad: String? = null,

    @SerialName("cntrecl")
    val cntrecl: Double? = null,

    @SerialName("nomcoord")
    val nomcoord: String? = null,

    @SerialName("cntpedidos")
    val cntpedidos: Double? = null,

    @SerialName("rlom_prcvisit")
    val rlomPrcvisit: Double? = null,

    @SerialName("cntfacturas")
    val cntfacturas: Double? = null,

    @SerialName("metavend")
    val metavend: Double? = null,

    @SerialName("vendedor")
    val vendedor: String? = null,

    @SerialName("nombrevend")
    val nombrevend: String? = null,

    @SerialName("defdol_totneto")
    val defdolTotneto: Double? = null,

    @SerialName("rlom_prcvtas")
    val rlomPrcvtas: Double? = null,

    @SerialName("lom_montovtas")
    val lomMontovtas: Double? = null,

    @SerialName("lom_prcvtas")
    val lomPrcvtas: Double? = null,

    @SerialName("lom_prcvisit")
    val lomPrcvisit: Double? = null,

    @SerialName("mtofacturas")
    val mtofacturas: Double? = null,

    @SerialName("clivisit")
    val clivisit: Double? = null,

    @SerialName("rlom_montovtas")
    val rlomMontovtas: Double? = null,

    @SerialName("prcvisitas")
    val prcvisitas: Double? = null,

    @SerialName("devdol_totneto")
    val devdolTotneto: Double? = null
)
