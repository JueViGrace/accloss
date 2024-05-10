package com.clo.accloss.orderlines.domain.mappers

import com.clo.accloss.orderlines.data.remote.model.OrderLinesItem
import com.clo.accloss.orderlines.domain.model.OrderLines
import com.clo.accloss.Lineas_pedido as OrderLinesEntity

fun OrderLinesEntity.toDomain(): OrderLines = OrderLines(
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

fun OrderLines.toDatabase(): OrderLinesEntity = OrderLinesEntity(
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

fun OrderLinesItem.toDomain(): OrderLines = OrderLines(
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
