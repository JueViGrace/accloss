package com.clo.accloss.pedido.domain.mappers

import com.clo.accloss.pedido.data.remote.model.PedidoItem
import com.clo.accloss.pedido.domain.model.Pedido
import com.clo.accloss.Pedido as PedidoEntity

fun PedidoEntity.toDomain(): Pedido = Pedido(
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

fun Pedido.toDatabase(): PedidoEntity = PedidoEntity(
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

fun PedidoItem.toDomain(): Pedido = Pedido(
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
