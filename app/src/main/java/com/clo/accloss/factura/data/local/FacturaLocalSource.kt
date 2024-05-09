package com.clo.accloss.factura.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Factura as FacturaEntity

class FacturaLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getFacturas(empresa: String): Flow<List<FacturaEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.facturaQueries
                .getFacturas(
                    empresa = empresa
                )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getFactura(
        documento: String,
        empresa: String
    ): Flow<FacturaEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.facturaQueries
                .getFactura(
                    documento = documento,
                    empresa = empresa
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addFactura(facturas: List<FacturaEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.facturaQueries.transaction {
                facturas.forEach { factura ->
                    db.facturaQueries.addFactura(factura)
                }
            }
        }
    }.await()
}
