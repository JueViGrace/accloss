package com.clo.accloss.customer.data.local

import com.clo.accloss.GetCustomerData
import com.clo.accloss.GetCustomersData
import com.clo.accloss.GetCustomersDataBySalesman
import kotlinx.coroutines.flow.Flow
import com.clo.accloss.Cliente as CustomerEntity

interface CustomerLocal {
    suspend fun getCustomers(company: String): Flow<List<CustomerEntity>>

    suspend fun getCustomersData(company: String): Flow<List<GetCustomersData>>

    suspend fun getCustomersDataBySalesman(
        company: String,
        salesman: String
    ): Flow<List<GetCustomersDataBySalesman>>

    suspend fun getCustomerData(
        company: String,
        id: String
    ): Flow<GetCustomerData?>

    suspend fun getCustomer(code: String, company: String): Flow<CustomerEntity>

    suspend fun addCustomer(customers: List<CustomerEntity>)
}
