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
}
