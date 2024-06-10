package com.clo.accloss.core.presentation.components

import com.clo.accloss.R

sealed class TopBarActions(
    val title: Int,
    val icon: Int? = null
) {
    data object Search : TopBarActions(
        title = R.string.search,
        icon = R.drawable.ic_search_24px
    )

    data object Statistics : TopBarActions(
        title = R.string.statistics,
        icon = R.drawable.ic_analytics_24px
    )

    data object Customers : TopBarActions(
        title = R.string.customers,
        icon = R.drawable.ic_groups_24px
    )
    data object Orders : TopBarActions(
        title = R.string.orders,
        icon = R.drawable.ic_shopping_bag_24px
    )

    data object Bills : TopBarActions(
        title = R.string.bills,
        icon = R.drawable.ic_receipt_24px
    )
}
