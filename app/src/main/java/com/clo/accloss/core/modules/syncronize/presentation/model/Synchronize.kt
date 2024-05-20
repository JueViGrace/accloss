package com.clo.accloss.core.modules.syncronize.presentation.model

import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.customer.domain.model.Customer
import com.clo.accloss.management.domain.model.Management
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.statistic.domain.model.Statistic

data class Synchronize(
    val session: Session? = null,
    val managements: RequestState<List<Management>> = RequestState.Idle,
    val salesmen: RequestState<List<Salesman>> = RequestState.Idle,
    val statistics: RequestState<List<Statistic>> = RequestState.Idle,
    val customers: RequestState<List<Customer>> = RequestState.Idle,
    val orders: RequestState<List<Order>> = RequestState.Idle,
    val bills: RequestState<List<Bill>> = RequestState.Idle,
    val products: RequestState<List<Product>> = RequestState.Idle,
    val sync: RequestState<Estado> = RequestState.Idle
)
