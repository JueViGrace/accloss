package com.clo.accloss.core.presentation.home.presentation.navigation.routes

import cafe.adriel.voyager.navigator.tab.Tab
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.HOME
import com.clo.accloss.core.common.Constants.PROFILE
import com.clo.accloss.core.presentation.dashboard.presentation.navigation.tab.DashboardTab
import com.clo.accloss.core.presentation.profile.presentation.navigation.tab.ProfileTab

sealed class HomeTabs(val tab: Tab, val index: UShort, val title: String, val icon: Int) {
    data object Dashboard : HomeTabs(
        tab = DashboardTab,
        index = 0u,
        title = HOME,
        icon = R.drawable.ic_home_app_logo_24px
    )
    data object Profile : HomeTabs(
        tab = ProfileTab,
        index = 1u,
        title = PROFILE,
        icon = R.drawable.ic_account_circle_24px
    )
}
