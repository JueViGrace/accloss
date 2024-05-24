package com.clo.accloss.session.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.data.database.helper.DbHelper
import com.clo.accloss.session.data.source.SessionDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import com.clo.accloss.Session as SessionEntity

class SessionLocalSourceImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : SessionDataSource {

    override suspend fun getCurrentUser(): Flow<SessionEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getCurrentUser()
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getCurrentSession(): SessionEntity? = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getCurrentUser()
                .executeAsOneOrNull()
        }
    }.await()

    override suspend fun getSessions(): List<SessionEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .getSessions()
                .executeAsList()
        }
    }.await()

    override suspend fun addSession(session: SessionEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.sessionQueries
                .addSession(session)
        }
    }.await()

    override suspend fun updateSession(session: SessionEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.sessionQueries.activateSession(
                    empresa = session.empresa,
                    lastSync = Date().toStringFormat()
                )
                db.sessionQueries.inactiveSessions(
                    empresa = session.empresa,
                )
            }
        }
    }.await()

    override suspend fun updateLastSync(lastSync: String, company: String) = scope.async {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.sessionQueries.updateLastSync(
                    empresa = company,
                    lastSync = lastSync
                )
            }
        }
    }.await()

    override suspend fun deleteSession(session: SessionEntity) = scope.async {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.tasasQueries.deleteTasas(session.empresa)
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
                db.configurationQueries.deleteConfig(session.empresa)
                db.sessionQueries.deleteSession(
                    user = session.user,
                    empresa = session.empresa
                )
            }
        }
    }.await()
}
