package com.clo.accloss.customer.domain.repository

import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.customer.data.source.CustomerDataSource
import com.clo.accloss.customer.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    val customerDataSource: CustomerDataSource

    suspend fun getRemoteCustomer(
        baseUrl: String,
        company: String,
        user: String
    ): RequestState<List<Customer>>

    fun getCustomers(
        company: String,
        baseUrl: String,
        user: String,
    ): Flow<RequestState<List<Customer>>>

    fun getCustomer(
        code: String,
        company: String
    ): Flow<RequestState<Customer>>

    suspend fun addCustomer(customers: List<Customer>)
}
