package com.clo.accloss.customer.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.clo.accloss.GetCustomerData
import com.clo.accloss.GetCustomersData
import com.clo.accloss.GetCustomersDataBySalesman
import com.clo.accloss.core.data.database.helper.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Cliente as CustomerEntity

class DefaultCustomerLocal(
    private val dbHelper: DbHelper,
    override val scope: CoroutineScope
) : CustomerLocal {
    override suspend fun getCustomers(company: String): Flow<List<CustomerEntity>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries
                    .getClientes(empresa = company)
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getCustomersData(company: String): Flow<List<GetCustomersData>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries
                    .getCustomersData(
                        empresa = company
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getCustomersDataBySalesman(
        company: String,
        salesman: String,
    ): Flow<List<GetCustomersDataBySalesman>> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries
                    .getCustomersDataBySalesman(
                        empresa = company,
                        vendedor = salesman,
                    )
                    .asFlow()
                    .mapToList(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getCustomerData(
        company: String,
        id: String
    ): Flow<GetCustomerData?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries.getCustomerData(
                    empresa = company,
                    codigo = id
                )
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun getCustomer(code: String, company: String): Flow<CustomerEntity?> {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries
                    .getCliente(
                        codigo = code,
                        empresa = company
                    )
                    .asFlow()
                    .mapToOneOrNull(scope.coroutineContext)
            }
        }.await()
    }

    override suspend fun addCustomer(customers: List<CustomerEntity>) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries.transaction {
                    customers.forEach { customer ->
                        db.clienteQueries.addCliente(customer)
                    }
                }
            }
        }.await()
    }

    override suspend fun deleteCustomers(company: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.clienteQueries.transaction {
                    db.clienteQueries.deleteClientes(empresa = company)
                }
            }
        }.await()
    }
}
