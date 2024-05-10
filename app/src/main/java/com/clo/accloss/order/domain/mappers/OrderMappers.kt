package com.clo.accloss.order.domain.mappers

import com.clo.accloss.order.data.remote.model.OrderItem
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.Pedido as PedidoEntity

fun PedidoEntity.toDomain(): Order = Order(
    fechamodifi = fechamodifi,
    kePedstatus = kePedstatus,
    ktiCodcli = ktiCodcli,
    ktiCodven = ktiCodven,
    ktiCondicion = ktiCondicion,
    ktiDocsol = ktiDocsol,
    ktiFchdoc = ktiFchdoc,
    ktiNdoc = ktiNdoc,
    ktiNegesp = ktiNegesp,
    ktiNombrecli = ktiNombrecli,
    ktiNroped = ktiNroped,
    ktiStatus = ktiStatus,
    ktiTdoc = ktiTdoc,
    ktiTipprec = ktiTipprec,
    ktiTotneto = ktiTotneto,
    empresa = empresa,
)

fun Order.toDatabase(): PedidoEntity = PedidoEntity(
    fechamodifi = fechamodifi,
    kePedstatus = kePedstatus,
    ktiCodcli = ktiCodcli,
    ktiCodven = ktiCodven,
    ktiCondicion = ktiCondicion,
    ktiDocsol = ktiDocsol,
    ktiFchdoc = ktiFchdoc,
    ktiNdoc = ktiNdoc,
    ktiNegesp = ktiNegesp,
    ktiNombrecli = ktiNombrecli,
    ktiNroped = ktiNroped,
    ktiStatus = ktiStatus,
    ktiTdoc = ktiTdoc,
    ktiTipprec = ktiTipprec,
    ktiTotneto = ktiTotneto,
    empresa = empresa,
)

fun OrderItem.toDomain(): Order = Order(
    fechamodifi = fechamodifi ?: "",
    kePedstatus = kePedstatus ?: "",
    ktiCodcli = ktiCodcli ?: "",
    ktiCodven = ktiCodven ?: "",
    ktiCondicion = ktiCondicion ?: "",
    ktiDocsol = ktiDocsol ?: "",
    ktiFchdoc = ktiFchdoc ?: "",
    ktiNdoc = ktiNdoc ?: "",
    ktiNegesp = ktiNegesp ?: "",
    ktiNombrecli = ktiNombrecli ?: "",
    ktiNroped = ktiNroped ?: "",
    ktiStatus = ktiStatus ?: "",
    ktiTdoc = ktiTdoc ?: "",
    ktiTipprec = ktiTipprec ?: 0.0,
    ktiTotneto = ktiTotneto ?: 0.0
)
