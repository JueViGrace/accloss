package com.clo.accloss.session.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Session as SessionEntity

class SessionLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getCurrentUser(): Flow<SessionEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getCurrentUser()
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun getSessions(): Flow<List<SessionEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getSessions()
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    suspend fun addSession(session: SessionEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .addSession(session)
        }
    }.await()

    suspend fun updateSession(session: SessionEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries.updateSessions(
                empresa = session.empresa
            )
        }
    }.await()

    suspend fun deleteSession(session: SessionEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.productQueries.deleteProducts(session.empresa)
                db.lineasFacturaQueries.deleteLineasFactura(session.empresa)
                db.lineasPedidoQueries.deleteLineasPedido(session.empresa)
                db.facturaQueries.deleteFacturas(session.empresa)
                db.pedidoQueries.deletePedidos(session.empresa)
                db.clienteQueries.deleteClientes(session.empresa)
                db.estadisticaQueries.deleteEstadisticas(session.empresa)
                db.vendedorQueries.deleteVendedor(session.empresa)
                db.gerenciaQueries.deleteGerencias(session.empresa)
                db.userQueries.deleteUser(session.user, session.empresa)
                db.empresaQueries.deleteEmpresa(session.empresa)
                db.sessionQueries.deleteSession(
                    user = session.user,
                    empresa = session.empresa
                )
            }
        }
    }.await()
}
