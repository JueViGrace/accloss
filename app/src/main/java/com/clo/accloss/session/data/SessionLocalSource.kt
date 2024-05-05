package com.clo.accloss.session.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.Session
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class SessionLocalSource(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) {
    suspend fun getCurrentUser(): Flow<Session> = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getCurrentUser()
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }
    }.await()

    suspend fun addSession(session: Session) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .addSession(session)
        }
    }.await()

    suspend fun updateSession(session: Session) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries.updateSessions(
                active = true,
                user = session.user,
                empresa = session.empresa
            )
        }
    }.await()

    suspend fun deleteSession(session: Session) = scope.async {
        dbHelper.withDatabase { db ->
            db.productQueries.deleteProducts(session.empresa)
            db.lineasFacturaQueries.deleteLineasFactura(session.empresa)
            db.lineasPedidoQueries.deleteLineasPedido(session.empresa)
            db.facturaQueries.deleteFacturas(session.empresa)
            db.pedidoQueries.deletePedidos(session.empresa)
            db.clienteQueries.deleteClientes(session.empresa)
            db.estadisticaQueries.deleteEstadisticas(session.empresa)
            db.vendedorQueries.deleteVendedor(session.empresa)
            db.gerenciaQueries.deleteGerencias(session.empresa)
            updateSession(session)
            db.userQueries.deleteUser(session.user, session.empresa)
            db.empresaQueries.deleteEmpresa(session.empresa)
            db.sessionQueries.deleteSession(
                user = session.user,
                empresa = session.empresa
            )
        }
    }.await()
}
