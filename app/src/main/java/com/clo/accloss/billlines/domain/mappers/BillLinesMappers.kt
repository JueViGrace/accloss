package com.clo.accloss.billlines.domain.mappers

import com.clo.accloss.billlines.data.remote.model.BillLinesItem
import com.clo.accloss.billlines.domain.model.BillLines
import com.clo.accloss.Lineas_factura as BillLinesEntity

fun BillLinesEntity.toDomain(): BillLines = BillLines(
    agencia = agencia,
    cantidad = cantidad,
    cntdevuelt = cntdevuelt,
    codcoord = codcoord,
    codhijo = codhijo,
    codigo = codigo,
    dmontoneto = dmontoneto,
    dmontototal = dmontototal,
    documento = documento,
    dpreciofin = dpreciofin,
    dpreciounit = dpreciounit,
    dvndmtototal = dvndmtototal,
    fechadoc = fechadoc,
    fechamodifi = fechamodifi,
    grupo = grupo,
    nombre = nombre,
    origen = origen,
    pid = pid,
    subgrupo = subgrupo,
    timpueprc = timpueprc,
    tipodoc = tipodoc,
    tipodocv = tipodocv,
    unidevuelt = unidevuelt,
    vendedor = vendedor,
    vndcntdevuelt = vndcntdevuelt,
    empresa = empresa,
)

fun BillLines.toDatabase(): BillLinesEntity = BillLinesEntity(
    agencia = agencia,
    cantidad = cantidad,
    cntdevuelt = cntdevuelt,
    codcoord = codcoord,
    codhijo = codhijo,
    codigo = codigo,
    dmontoneto = dmontoneto,
    dmontototal = dmontototal,
    documento = documento,
    dpreciofin = dpreciofin,
    dpreciounit = dpreciounit,
    dvndmtototal = dvndmtototal,
    fechadoc = fechadoc,
    fechamodifi = fechamodifi,
    grupo = grupo,
    nombre = nombre,
    origen = origen,
    pid = pid,
    subgrupo = subgrupo,
    timpueprc = timpueprc,
    tipodoc = tipodoc,
    tipodocv = tipodocv,
    unidevuelt = unidevuelt,
    vendedor = vendedor,
    vndcntdevuelt = vndcntdevuelt,
    empresa = empresa,
)

fun BillLinesItem.toDomain(): BillLines = BillLines(
    agencia = agencia ?: "",
    cantidad = cantidad ?: 0.0,
    cntdevuelt = cntdevuelt ?: 0.0,
    codcoord = codcoord ?: "",
    codhijo = codhijo ?: "",
    codigo = codigo ?: "",
    dmontoneto = dmontoneto ?: 0.0,
    dmontototal = dmontototal ?: 0.0,
    documento = documento ?: "",
    dpreciofin = dpreciofin ?: 0.0,
    dpreciounit = dpreciounit ?: 0.0,
    dvndmtototal = dvndmtototal ?: 0.0,
    fechadoc = fechadoc ?: "",
    fechamodifi = fechamodifi ?: "",
    grupo = grupo ?: "",
    nombre = nombre ?: "",
    origen = origen ?: 0.0,
    pid = pid ?: "",
    subgrupo = subgrupo ?: "",
    timpueprc = timpueprc ?: 0.0,
    tipodoc = tipodoc ?: "",
    tipodocv = tipodocv ?: "",
    unidevuelt = unidevuelt ?: 0.0,
    vendedor = vendedor ?: "",
    vndcntdevuelt = vndcntdevuelt ?: 0.0
)