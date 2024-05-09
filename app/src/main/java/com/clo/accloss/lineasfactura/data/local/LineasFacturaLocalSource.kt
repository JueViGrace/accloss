package com.clo.accloss.lineasfactura.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Lineas_factura as LineasFacturaEntity

class LineasFacturaLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getLineasFactura(
        documento: String,
        empresa: String
    ): Flow<List<LineasFacturaEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasFacturaQueries.getLineasFactura(
                documento = documento,
                empresa = empresa
            )
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addLineasFactura(lineasFactura: List<LineasFacturaEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.lineasFacturaQueries.transaction {
                lineasFactura.forEach { linea ->
                    db.lineasFacturaQueries.addLineasFactura(linea)
                }
            }
        }
    }.await()
}
