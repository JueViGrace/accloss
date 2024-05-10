package com.clo.accloss.customer.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(

    @SerialName("clientes")
    val customers: List<CustomerItem> = emptyList(),

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class CustomerItem(

    @SerialName("promdiasp")
    val promdiasp: Double? = null,

    @SerialName("fchcrea")
    val fchcrea: String? = null,

    @SerialName("cantdocs")
    val cantdocs: Double? = null,

    @SerialName("perscont")
    val perscont: String? = null,

    @SerialName("nombre")
    val nombre: String? = null,

    @SerialName("prcdpagdia")
    val prcdpagdia: Double? = null,

    @SerialName("kne_mtomin")
    val kneMtomin: Double? = null,

    @SerialName("precio")
    val precio: Double? = null,

    @SerialName("contribespecial")
    val contribespecial: Double? = null,

    @SerialName("noeminota")
    val noeminota: Int? = null,

    @SerialName("telefonos")
    val telefonos: String? = null,

    @SerialName("promdiasvta")
    val promdiasvta: Double? = null,

    @SerialName("subcodigo")
    val subcodigo: String? = null,

    @SerialName("sector")
    val sector: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("mtoultvta")
    val mtoultvta: Double? = null,

    @SerialName("fchultvta")
    val fchultvta: String? = null,

    @SerialName("codigo")
    val codigo: String? = null,

    @SerialName("vendedor")
    val vendedor: String? = null,

    @SerialName("limcred")
    val limcred: Double? = null,

    @SerialName("direccion")
    val direccion: String? = null,

    @SerialName("totmtodocs")
    val totmtodocs: Double? = null,

    @SerialName("prommtodoc")
    val prommtodoc: Double? = null,

    @SerialName("riesgocrd")
    val riesgocrd: Double? = null,

    @SerialName("kne_activa")
    val kneActiva: String? = null,

    @SerialName("diasultvta")
    val diasultvta: Double? = null,

    @SerialName("fechamodifi")
    val fechamodifi: String? = null,

    @SerialName("noemifac")
    val noemifac: Int? = null,

    @SerialName("status")
    val status: Double? = null
)
