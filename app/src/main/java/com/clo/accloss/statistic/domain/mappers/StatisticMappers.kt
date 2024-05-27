package com.clo.accloss.statistic.domain.mappers

import com.clo.accloss.GetManagementStatistics
import com.clo.accloss.GetManagementsStatistics
import com.clo.accloss.GetProfileStatistics
import com.clo.accloss.GetSalesmanPersonalStatistic
import com.clo.accloss.core.modules.profile.presentation.model.ProfileStatisticsModel
import com.clo.accloss.statistic.data.remote.model.StatisticResponse
import com.clo.accloss.statistic.domain.model.Statistic
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import com.clo.accloss.Estadistica as StatisticEntity

fun StatisticEntity.toDomain(): Statistic = Statistic(
    clivisit = clivisit,
    cntclientes = cntclientes,
    cntfacturas = cntfacturas,
    cntpedidos = cntpedidos,
    cntrecl = cntrecl,
    codcoord = codcoord,
    defdolTotneto = defdolTotneto,
    devdolTotneto = devdolTotneto,
    fechaEstad = fechaEstad,
    lomMontovtas = lomMontovtas,
    lomPrcvisit = lomPrcvisit,
    lomPrcvtas = lomPrcvtas,
    mtofacturas = mtofacturas,
    mtopedidos = mtopedidos,
    mtorecl = mtorecl,
    metavend = metavend,
    nombrevend = nombrevend,
    nomcoord = nomcoord,
    ppgdolTotneto = ppgdolTotneto,
    prcmeta = prcmeta,
    prcvisitas = prcvisitas,
    rlomMontovtas = rlomMontovtas,
    rlomPrcvisit = rlomPrcvisit,
    rlomPrcvtas = rlomPrcvtas,
    totdolcob = totdolcob,
    vendedor = vendedor,
    empresa = empresa
)

fun Statistic.toDatabase(): StatisticEntity = StatisticEntity(
    clivisit = clivisit,
    cntclientes = cntclientes,
    cntfacturas = cntfacturas,
    cntpedidos = cntpedidos,
    cntrecl = cntrecl,
    codcoord = codcoord,
    defdolTotneto = defdolTotneto,
    devdolTotneto = devdolTotneto,
    fechaEstad = fechaEstad,
    lomMontovtas = lomMontovtas,
    lomPrcvisit = lomPrcvisit,
    lomPrcvtas = lomPrcvtas,
    mtofacturas = mtofacturas,
    mtopedidos = mtopedidos,
    mtorecl = mtorecl,
    metavend = metavend,
    nombrevend = nombrevend,
    nomcoord = nomcoord,
    ppgdolTotneto = ppgdolTotneto,
    prcmeta = prcmeta,
    prcvisitas = prcvisitas,
    rlomMontovtas = rlomMontovtas,
    rlomPrcvisit = rlomPrcvisit,
    rlomPrcvtas = rlomPrcvtas,
    totdolcob = totdolcob,
    vendedor = vendedor,
    empresa = empresa
)

fun StatisticResponse.toDomain(): Statistic = Statistic(
    clivisit = clivisit ?: 0.0,
    cntclientes = cntclientes ?: 0.0,
    cntfacturas = cntfacturas ?: 0.0,
    cntpedidos = cntpedidos ?: 0.0,
    cntrecl = cntrecl ?: 0.0,
    codcoord = codcoord ?: "",
    defdolTotneto = defdolTotneto ?: 0.0,
    devdolTotneto = devdolTotneto ?: 0.0,
    fechaEstad = fechaEstad ?: "",
    lomMontovtas = lomMontovtas ?: 0.0,
    lomPrcvisit = lomPrcvisit ?: 0.0,
    lomPrcvtas = lomPrcvtas ?: 0.0,
    metavend = metavend ?: 0.0,
    mtofacturas = mtofacturas ?: 0.0,
    mtopedidos = mtopedidos ?: 0.0,
    mtorecl = mtorecl ?: 0.0,
    nombrevend = nombrevend ?: "",
    nomcoord = nomcoord ?: "",
    ppgdolTotneto = ppgdolTotneto ?: 0.0,
    prcmeta = prcmeta ?: 0.0,
    prcvisitas = prcvisitas ?: 0.0,
    rlomMontovtas = rlomMontovtas ?: 0.0,
    rlomPrcvisit = rlomPrcvisit ?: 0.0,
    rlomPrcvtas = rlomPrcvtas ?: 0.0,
    totdolcob = totdolcob ?: 0.0,
    vendedor = vendedor ?: ""
)

fun GetProfileStatistics.toUi(): ProfileStatisticsModel = ProfileStatisticsModel(
    debts = debts ?: 0.0,
    paid = paid ?: 0.0,
    expired = expired ?: 0.0
)

fun GetManagementsStatistics.toUi(): PersonalStatistics = PersonalStatistics(
    nombre = nombre,
    codigo = codigo,
)

fun GetManagementStatistics.toUi(): PersonalStatistics = PersonalStatistics(
    prcmeta = prcmeta ?: 0.0,
    mtofactneto = mtofactneto ?: 0.0,
    cantped = cantped ?: 0.0,
    meta = meta ?: 0.0,
    mtocob = mtocob ?: 0.0,
    deuda = deuda ?: 0.0,
    vencido = vencido ?: 0.0,
    promdiasvta = promdiasvta ?: 0.0,
    cantdocs = cantdocs ?: 0.0,
    totmtodocs = totmtodocs ?: 0.0,
    prommtopordoc = prommtopordoc ?: 0.0,
    nombre = nombre,
    codigo = codigo,
)

fun GetSalesmanPersonalStatistic.toUi(): PersonalStatistics = PersonalStatistics(
    prcmeta = prcmeta ?: 0.0,
    mtofactneto = mtofactneto ?: 0.0,
    cantped = cantped ?: 0.0,
    meta = meta ?: 0.0,
    mtocob = mtocob ?: 0.0,
    deuda = deuda ?: 0.0,
    vencido = vencido ?: 0.0,
    promdiasvta = promdiasvta ?: 0.0,
    cantdocs = cantdocs ?: 0.0,
    totmtodocs = totmtodocs ?: 0.0,
    prommtopordoc = prommtopordoc ?: 0.0,
    nombre = nombre,
    codigo = codigo,
)
