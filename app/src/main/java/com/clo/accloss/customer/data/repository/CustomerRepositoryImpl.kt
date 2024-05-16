package com.clo.accloss.customer.data.repository

import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.log
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.customer.data.source.CustomerDataSource
import com.clo.accloss.customer.domain.mappers.toDatabase
import com.clo.accloss.customer.domain.mappers.toDomain
import com.clo.accloss.customer.domain.model.Customer
import com.clo.accloss.customer.domain.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CustomerRepositoryImpl(
    override val customerDataSource: CustomerDataSource
) : CustomerRepository {
    override suspend fun getRemoteCustomer(
        baseUrl: String,
        company: String,
        user: String
    ): RequestState<List<Customer>> {
        return withContext(Dispatchers.IO) {
            when (
                val apiOperation = customerDataSource.customerRemote
                    .getSafeCustomers(
                        baseUrl = baseUrl,
                        user = user
                    )
            ) {
                is ApiOperation.Failure -> {
                    RequestState.Error(
                        message = apiOperation.error
                    )
                }

                is ApiOperation.Success -> {
                    val data = apiOperation.data.customers.map { customerItem ->
                        customerItem.toDomain().copy(empresa = company)
                    }

                    addCustomer(data)

                    RequestState.Success(
                        data = data
                    )
                }
            }
        }
    }

    override fun getCustomers(
        company: String,
        baseUrl: String,
        user: String,
    ): Flow<RequestState<List<Customer>>> = flow<RequestState<List<Customer>>> {
        emit(RequestState.Loading)

        customerDataSource.customerLocal.getCustomers(
            company = company
        ).catch { e ->
            emit(RequestState.Error(message = DB_ERROR_MESSAGE))
            e.log("CUSTOMER REPOSITORY: getCustomers")
        }.collect { cachedList ->
            emit(
                RequestState.Success(
                    data = cachedList.map { customerEntity ->
                        customerEntity.toDomain()
                    }
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override fun getCustomer(
        code: String,
        company: String
    ): Flow<RequestState<Customer>> = flow<RequestState<Customer>> {
        emit(RequestState.Loading)

        customerDataSource.customerLocal.getCustomer(
            code = code,
            company = company
        ).catch { e ->
            emit(RequestState.Error(message = DB_ERROR_MESSAGE))
            e.log("CUSTOMER REPOSITORY: getCustomer")
        }.collect { customerEntity ->
            emit(
                RequestState.Success(
                    data = customerEntity.toDomain()
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addCustomer(customers: List<Customer>) =
        customerDataSource.customerLocal.addCustomer(
            customers = customers.map { customer ->
                customer.toDatabase()
            }
        )
}
