package com.clo.accloss.customer.data.local

import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Cliente as CustomerEntity

interface CustomerLocal {
    suspend fun getCustomers(company: String): Flow<List<CustomerEntity>>

    suspend fun getCustomer(code: String, company: String): Flow<CustomerEntity>

    suspend fun addCustomer(customers: List<CustomerEntity>)
}
