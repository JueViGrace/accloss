package com.clo.accloss.order.domain.mappers

import com.clo.accloss.GetOrderWithLines
import com.clo.accloss.order.data.remote.model.OrderItem
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.order.presentation.model.OrderDetails
import com.clo.accloss.orderlines.domain.model.OrderLines
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

fun List<GetOrderWithLines>.toUi(): OrderDetails {
    val group = this.groupBy { order ->
        Order(
            fechamodifi = order.fechamodifi,
            kePedstatus = order.kePedstatus,
            ktiCodcli = order.ktiCodcli,
            ktiCodven = order.ktiCodven,
            ktiCondicion = order.ktiCondicion,
            ktiDocsol = order.ktiDocsol,
            ktiFchdoc = order.ktiFchdoc,
            ktiNdoc = order.ktiNdoc,
            ktiNegesp = order.ktiNegesp,
            ktiNombrecli = order.ktiNombrecli,
            ktiNroped = order.ktiNroped,
            ktiStatus = order.ktiStatus,
            ktiTdoc = order.ktiTdoc,
            ktiTipprec = order.ktiTipprec,
            ktiTotneto = order.ktiTotneto,
            empresa = order.empresa,
        )
    }

    var orderDetails = OrderDetails()

    group.forEach { (key, value) ->
        orderDetails = orderDetails.copy(
            order = key,
            orderLines = value.map { getOrderWithLines ->
                OrderLines(
                    kmvArtprec = getOrderWithLines.kmvArtprec ?: 0.0,
                    kmvCant = getOrderWithLines.kmvCant ?: 0.0,
                    kmvCodart = getOrderWithLines.kmvCodart ?: "",
                    kmvDctolin = getOrderWithLines.kmvDctolin ?: 0.0,
                    kmvNombre = getOrderWithLines.kmvNombre ?: "",
                    kmvStot = getOrderWithLines.kmvStot ?: 0.0,
                    ktiNdoc = getOrderWithLines.ktiNdoc_ ?: "",
                    ktiTdoc = getOrderWithLines.ktiTdoc_ ?: "",
                    ktiTipprec = getOrderWithLines.ktiTipprec_ ?: 0.0,
                    empresa = getOrderWithLines.empresa_ ?: "",
                )
            }
        )
    }

    return orderDetails
}
