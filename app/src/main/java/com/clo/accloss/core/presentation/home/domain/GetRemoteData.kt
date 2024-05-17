package com.clo.accloss.core.presentation.home.domain

import android.util.Log
import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.customer.domain.repository.CustomerRepository
import com.clo.accloss.management.domain.repository.ManagementRepository
import com.clo.accloss.order.data.repository.OrderRepositoryImpl
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.rate.domain.repository.RateRepository
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
    private val rateRepository: RateRepository,
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

                    val rateResult = rateRepository.getRemoteRate(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        company = sessionResult.data.empresa,
                    )

                    if (rateResult.isSuccess()) {
                        Log.i("Get Rate", "Rate: ${rateResult.getSuccessData()}")
                    }

                    val managementResult = managementRepository.getRemoteManagements(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    )

                    if (managementResult.isSuccess()){
                        Log.i("Get Managements", "Managements: ${managementResult.getSuccessData()}")
                    }

                    val salesmanResult = salesmanRepository.getRemoteSalesman(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa
                    )

                    if (salesmanResult.isSuccess()) {
                        Log.i("Get Salesmen", "Salesmen: ${salesmanResult.getSuccessData()}")
                    }

                    val statisticResult = statisticRepository.getRemoteStatistics(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    )

                    if (statisticResult.isSuccess()){
                        Log.i("Get Statistics", "Statistics: ${statisticResult.getSuccessData()} ")
                    }

                    val customerResult = customerRepository.getRemoteCustomer(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    )

                    if (customerResult.isSuccess()) {
                        Log.i("Get Customers", "Customers: ${customerResult.getSuccessData()} ")
                    }

                    val orderResult = orderRepository.getRemoteOrders(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    )
                    if (orderResult.isSuccess()){
                        Log.i("Get Orders", "Orders: ${orderResult.getSuccessData()} ")
                    }

                    val billResult = billRepository.getRemoteBills(
                        baseUrl = sessionResult.data.enlaceEmpresa,
                        user = sessionResult.data.user,
                        company = sessionResult.data.empresa,
                    )

                    if (billResult.isSuccess()){
                        Log.i("Get Bills", "Bills: ${billResult.getSuccessData()} ")
                    }
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}
