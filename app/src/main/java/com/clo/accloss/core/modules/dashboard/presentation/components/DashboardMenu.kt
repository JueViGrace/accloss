package com.clo.accloss.core.modules.dashboard.presentation.components

import com.clo.accloss.R

sealed class DashboardMenu(
    val name: Int,
    val icon: Int
) {
    data object Catalogue : DashboardMenu(
        name = R.string.catalogue,
        icon = R.drawable.ic_inventory_2_24px
    )
    data object Offers : DashboardMenu(
        name = R.string.offers,
        icon = R.drawable.ic_shopping_bag_24px
    )
    data object Orders : DashboardMenu(
        name = R.string.orders,
        icon = R.drawable.ic_shopping_bag_24px
    )
    data object Bills : DashboardMenu(
        name = R.string.bills,
        icon = R.drawable.ic_receipt_24px
    )
    data object Managements : DashboardMenu(
        name = R.string.managements,
        icon = R.drawable.ic_supervisor_account_24px
    )
    data object Salesmen : DashboardMenu(
        name = R.string.salesmen,
        icon = R.drawable.ic_corporate_fare_24px
    )
    data object Customers : DashboardMenu(
        name = R.string.customers,
        icon = R.drawable.ic_groups_24px
    )
}
