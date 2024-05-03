package com.clo.accloss.lineaspedido.domain.mappers

import com.clo.accloss.lineaspedido.data.remote.model.LineasPedidosItem
import com.clo.accloss.lineaspedido.domain.model.LineasPedido
import com.clo.accloss.Lineas_pedido as LineasPedidoEntity

fun LineasPedidoEntity.toDomain(): LineasPedido = LineasPedido(
    kmvArtprec = kmvArtprec,
    kmvCant = kmvCant,
    kmvCodart = kmvCodart,
    kmvDctolin = kmvDctolin,
    kmvNombre = kmvNombre,
    kmvStot = kmvStot,
    ktiNdoc = ktiNdoc,
    ktiTdoc = ktiTdoc,
    ktiTipprec = ktiTipprec,
    empresa = empresa,
)

fun LineasPedido.toDatabase(): LineasPedidoEntity = LineasPedidoEntity(
    kmvArtprec = kmvArtprec,
    kmvCant = kmvCant,
    kmvCodart = kmvCodart,
    kmvDctolin = kmvDctolin,
    kmvNombre = kmvNombre,
    kmvStot = kmvStot,
    ktiNdoc = ktiNdoc,
    ktiTdoc = ktiTdoc,
    ktiTipprec = ktiTipprec,
    empresa = empresa,
)

fun LineasPedidosItem.toDomain(): LineasPedido = LineasPedido(
    kmvArtprec = kmvArtprec ?: 0.0,
    kmvCant = kmvCant ?: 0.0,
    kmvCodart = kmvCodart ?: "",
    kmvDctolin = kmvDctolin ?: 0.0,
    kmvNombre = kmvNombre ?: "",
    kmvStot = kmvStot ?: 0.0,
    ktiNdoc = ktiNdoc ?: "",
    ktiTdoc = ktiTdoc ?: "",
    ktiTipprec = ktiTipprec ?: 0.0,
)
