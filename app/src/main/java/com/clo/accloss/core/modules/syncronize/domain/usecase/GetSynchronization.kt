package com.clo.accloss.core.modules.syncronize.domain.usecase

import com.clo.accloss.bills.domain.repository.BillRepository
import com.clo.accloss.configuration.domain.repository.ConfigurationRepository
import com.clo.accloss.core.common.Constants.APP_VERSION
import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.modules.syncronize.presentation.model.Synchronization
import com.clo.accloss.core.modules.syncronize.presentation.model.Synchronize
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.customer.domain.repository.CustomerRepository
import com.clo.accloss.management.domain.repository.ManagementRepository
import com.clo.accloss.order.domain.repository.OrderRepository
import com.clo.accloss.products.domain.repository.ProductRepository
import com.clo.accloss.salesman.domain.repository.SalesmanRepository
import com.clo.accloss.session.domain.repository.SessionRepository
import com.clo.accloss.session.domain.usecase.GetCurrentSession
import com.clo.accloss.statistic.domain.repository.StatisticRepository
import com.clo.accloss.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

//TODO: DELETE ALL AND INSERT
class GetSynchronization(
    private val getCurrentSession: GetCurrentSession,
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
    private val managementRepository: ManagementRepository,
    private val salesmanRepository: SalesmanRepository,
    private val statisticRepository: StatisticRepository,
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
    private val billRepository: BillRepository,
    private val productRepository: ProductRepository,
    private val configurationRepository: ConfigurationRepository
) {
    operator fun invoke(): Flow<RequestState<Synchronize>> = flow {
        emit(RequestState.Loading)

        getCurrentSession().collect { sessionResult ->
            when (sessionResult) {
                is RequestState.Error -> {
                    emit(
                        RequestState.Error(message = sessionResult.message)
                    )
                }
                is RequestState.Success -> {
                    configurationRepository.deleteConfiguration(company = sessionResult.data.empresa)
                    managementRepository.deleteManagements(company = sessionResult.data.empresa)
                    salesmanRepository.deleteSalesmen(company = sessionResult.data.empresa)
                    statisticRepository.deleteStatistics(company = sessionResult.data.empresa)
                    customerRepository.deleteCustomers(company = sessionResult.data.empresa)
                    orderRepository.deleteOrders(company = sessionResult.data.empresa)
                    billRepository.deleteBills(company = sessionResult.data.empresa)
                    productRepository.deleteProducts(company = sessionResult.data.empresa)

                    var synchronize = Synchronize()

                    synchronize = synchronize.copy(
                        session = sessionResult.data
                    )

                    synchronize = synchronize.copy(
                        configuration = configurationRepository.getRemoteConfiguration(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            salesman = sessionResult.data.user,
                            company = sessionResult.data.empresa
                        )
                    )

                    synchronize = synchronize.copy(
                        managements = managementRepository.getRemoteManagements(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            user = sessionResult.data.user,
                            company = sessionResult.data.empresa,
                        )
                    )

                    synchronize = synchronize.copy(
                        salesmen = salesmanRepository.getRemoteSalesman(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            user = sessionResult.data.user,
                            company = sessionResult.data.empresa
                        )
                    )

                    synchronize = synchronize.copy(
                        statistics = statisticRepository.getRemoteStatistics(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            user = sessionResult.data.user,
                            company = sessionResult.data.empresa,
                        )
                    )

                    synchronize = synchronize.copy(
                        customers = customerRepository.getRemoteCustomer(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            user = sessionResult.data.user,
                            company = sessionResult.data.empresa,
                        )
                    )

                    synchronize = synchronize.copy(
                        orders = orderRepository.getRemoteOrders(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            user = sessionResult.data.user,
                            company = sessionResult.data.empresa,
                        )
                    )

                    synchronize = synchronize.copy(
                        bills = billRepository.getRemoteBills(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            user = sessionResult.data.user,
                            company = sessionResult.data.empresa,
                        )
                    )

                    synchronize = synchronize.copy(
                        products = productRepository.getRemoteProducts(
                            baseUrl = sessionResult.data.enlaceEmpresa,
                            lastSync = "1000:01:01 00:00:00",
                            company = sessionResult.data.empresa,
                        )
                    )

                    synchronize = synchronize.copy(
                        sync = userRepository.synchronize(
                            baseUrl = sessionResult.data.enlaceEmpresaPost,
                            sync = Synchronization(
                                usuario = sessionResult.data.user,
                                fecha = Date().toStringFormat(),
                                version = APP_VERSION
                            )
                        )
                    )

                    userRepository.updateSyncDate(
                        lastSync = Date().toStringFormat(),
                        company = sessionResult.data.empresa
                    )

                    sessionRepository.updateLastSync(
                        lastSync = Date().toStringFormat(),
                        company = sessionResult.data.empresa
                    )

                    emit(
                        RequestState.Success(
                            data = synchronize
                        )
                    )
                }
                else -> emit(RequestState.Loading)
            }
        }
    }.flowOn(Dispatchers.IO)
}
