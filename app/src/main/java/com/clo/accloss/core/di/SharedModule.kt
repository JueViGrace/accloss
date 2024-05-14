package com.clo.accloss.core.di

import com.clo.accloss.billlines.di.billLinesModule
import com.clo.accloss.bills.di.billModule
import com.clo.accloss.company.di.companyModule
import com.clo.accloss.customer.di.customerModule
import com.clo.accloss.login.di.authModule
import com.clo.accloss.management.di.managementModule
import com.clo.accloss.order.di.orderModule
import com.clo.accloss.orderlines.di.orderLinesModule
import com.clo.accloss.products.di.productModule
import com.clo.accloss.rate.di.rateModule
import com.clo.accloss.salesman.di.salesmanModule
import com.clo.accloss.session.di.sessionModule
import com.clo.accloss.statistic.di.statisticModule
import com.clo.accloss.user.di.userModule
import org.koin.dsl.module

val sharedModule = module {
    includes(
        authModule,
        companyModule,
        userModule,
        sessionModule,
        productModule,
        managementModule,
        customerModule,
        salesmanModule,
        statisticModule,
        orderModule,
        billModule,
        billLinesModule,
        orderLinesModule,
        rateModule,
    )
}
