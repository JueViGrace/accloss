package com.clo.accloss.core.presentation.home.domain

import android.util.Log
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.customer.domain.repository.CustomerRepository
import com.clo.accloss.management.domain.repository.ManagementRepository
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.domain.usecase.GetSession
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRemoteData(
    private val getSession: GetSession,
    private val managementRepository: ManagementRepository,
    private val salesmanRepository: SalesmanRepository,
    private val statisticRepository: StatisticRepository,
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
    private val billRepository: BillRepository
) {
    operator fun invoke(): Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)
        getSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(
                            message = sessionResult.message
                        )
                    )
                }
                is RequestState.Success -> {
                    emit(
                        RequestState.Success(
                            data = sessionResult.data
                        )
                    )

                    managementRepository.getRemoteGerencia(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    ).collect {
                        Log.i("Get Managements", "Managements: ${it.getSuccessDataOrNull()}")
                    }

                    val salesmanResult = salesmanRepository.getRemoteSalesman(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa
                    )

                    if (salesmanResult.isSuccess()) {
                        Log.i("Get Salesmen", "Salesmen: ${salesmanResult.getSuccessData()}")
                    }

                    statisticRepository.getRemoteStatistics(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    ).collect {
                        Log.i("Get Statistics", "Statistics: ${it.getSuccessDataOrNull()} ")
                    }

                    val customerResult = customerRepository.getRemoteCustomer(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    )

                    if (customerResult.isSuccess()) {
                        Log.i("Get Customers", "Customers: ${customerResult.getSuccessData()} ")
                    }

                    orderRepository.getRemoteOrders(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    ).collect {
                        Log.i("Get Orders", "Orders: ${it.getSuccessDataOrNull()} ")
                    }

                    billRepository.getRemoteBills(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    ).collect {
                        Log.i("Get Bills", "Bills: ${it.getSuccessDataOrNull()} ")
                    }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}
