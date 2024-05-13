package com.clo.accloss.customer.domain.repository

import android.util.Log
import com.clo.accloss.core.common.Constants.DB_ERROR_MESSAGE
import com.clo.accloss.core.common.Constants.SERVER_ERROR
import com.clo.accloss.core.data.network.ApiOperation
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.customer.data.local.CustomerLocalSource
import com.clo.accloss.customer.data.remote.source.CustomerRemoteSource
import com.clo.accloss.customer.domain.mappers.toDatabase
import com.clo.accloss.customer.domain.mappers.toDomain
import com.clo.accloss.customer.domain.model.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CustomerRepository(
    private val customerRemoteSource: CustomerRemoteSource,
    private val customerLocalSource: CustomerLocalSource
) {
    fun getRemoteCustomer(
        baseUrl: String,
        company: String,
        user: String
    ): Flow<RequestState<List<Customer>>> = flow {
        emit(RequestState.Loading)

        val apiOperation = customerRemoteSource
            .getSafeCustomers(
                baseUrl = baseUrl,
                user = user
            )

        when (apiOperation) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        message = apiOperation.error
                    )
                )
            }
            is ApiOperation.Success -> {
                val data = apiOperation.data.customers.map { customerItem ->
                    customerItem.toDomain().copy(empresa = company)
                }

                addCustomer(data)

                emit(
                    RequestState.Success(
                        data = data
                    )
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getCustomers(
        company: String,
        baseUrl: String,
        user: String,
        forceReload: Boolean = false
    ): Flow<RequestState<List<Customer>>> = flow<RequestState<List<Customer>>> {
        emit(RequestState.Loading)

        var reload = forceReload

        customerLocalSource.getCustomers(company)
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { cachedList ->
                if (cachedList.isNotEmpty() && !reload) {
                    emit(
                        RequestState.Success(
                            data = cachedList.map { customerEntity ->
                                customerEntity.toDomain()
                            }
                        )
                    )
                } else {
                    getRemoteCustomer(
                        baseUrl = baseUrl,
                        company = company,
                        user = user
                    ).collect { result ->
                        when (result) {
                            is RequestState.Error -> {
                                emit(
                                    RequestState.Error(
                                        message = result.message
                                    )
                                )
                            }
                            is RequestState.Success -> {
                                Log.i("Force reload Customers", "getCustomers: ${result.data}")
                            }
                            else -> emit(RequestState.Loading)
                        }
                    }
                    reload = false
                }
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getCustomer(
        code: String,
        company: String
    ): Flow<RequestState<Customer>> = flow<RequestState<Customer>> {
        emit(RequestState.Loading)

        customerLocalSource.getCustomer(
            code = code,
            company = company
        )
            .catch { e ->
                emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
            }
            .collect { customerEntity ->
                emit(
                    RequestState.Success(
                        data = customerEntity.toDomain()
                    )
                )
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun addCustomer(customers: List<Customer>) =
        customerLocalSource.addCustomer(
            customers = customers.map { customer ->
                customer.toDatabase()
            }
        )
}
