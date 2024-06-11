package com.clo.accloss.core.modules.home.presentation.navigation.routes

import cafe.adriel.voyager.navigator.tab.Tab
import com.clo.accloss.R
import com.clo.accloss.core.modules.contact.presentation.navigation.tab.ContactsTab
import com.clo.accloss.core.modules.dashboard.presentation.tab.DashboardTab
import com.clo.accloss.core.modules.profile.presentation.tab.ProfileTab

sealed class HomeTabs(val tab: Tab, val index: UShort, val title: Int, val icon: Int) {
    data object Dashboard : HomeTabs(
        tab = DashboardTab,
        index = 0u,
        title = R.string.home,
        icon = R.drawable.ic_home_app_logo_24px
    )
    data object Contact : HomeTabs(
        tab = ContactsTab,
        index = 1u,
        title = R.string.contacts,
        icon = R.drawable.ic_contacts_24px
    )
    data object Profile : HomeTabs(
        tab = ProfileTab,
        index = 2u,
        title = R.string.profile,
        icon = R.drawable.ic_account_circle_24px
    )
}
