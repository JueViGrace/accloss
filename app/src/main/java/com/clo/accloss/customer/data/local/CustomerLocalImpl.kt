package com.clo.accloss.customer.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.clo.accloss.Cliente as CustomerEntity

class CustomerLocalImpl(
    private val dbHelper: DbHelper,
    private val scope: CoroutineScope
) : CustomerLocal {
    override suspend fun getCustomers(company: String): Flow<List<CustomerEntity>> = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries
                .getClientes(empresa = company)
                .asFlow()
                .mapToList(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun getCustomer(code: String, company: String): Flow<CustomerEntity> = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries
                .getCliente(
                    codigo = code,
                    empresa = company
                )
                .asFlow()
                .mapToOne(scope.coroutineContext)
        }.flowOn(Dispatchers.IO)
    }.await()

    override suspend fun addCustomer(customers: List<CustomerEntity>) = scope.async {
        dbHelper.withDatabase { db ->
            db.clienteQueries.transaction {
                customers.forEach { customer ->
                    db.clienteQueries.addCliente(customer)
                }
            }
        }
    }.await()
}
