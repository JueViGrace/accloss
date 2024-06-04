package com.clo.accloss.customer.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.customer.data.source.CustomerDataSource
import com.clo.accloss.customer.domain.model.Customer
import com.clo.accloss.customer.presentation.model.CustomerData
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CustomerRepository {
    val customerDataSource: CustomerDataSource
    val coroutineContext: CoroutineContext

    suspend fun getRemoteCustomer(
        baseUrl: String,
        company: String,
        user: String
    ): RequestState<List<Customer>>

    fun getCustomers(
        company: String,
    ): Flow<RequestState<List<Customer>>>

    fun getCustomersData(
        company: String
    ): Flow<RequestState<List<CustomerData>>>

    fun getCustomersDataBySalesman(
        company: String,
        salesman: String
    ): Flow<RequestState<List<CustomerData>>>

    fun getCustomerData(
        company: String,
        id: String,
    ): Flow<RequestState<CustomerData>>

    fun getCustomer(
        code: String,
        company: String
    ): Flow<RequestState<Customer>>

    suspend fun addCustomer(customers: List<Customer>)
}
