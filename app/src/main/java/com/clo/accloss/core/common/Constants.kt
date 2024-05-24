package com.clo.accloss.core.common

import cafe.adriel.voyager.navigator.tab.Tab
import com.clo.accloss.BuildConfig
import com.clo.accloss.core.modules.dashboard.presentation.components.DashboardMenu
import com.clo.accloss.core.modules.home.presentation.navigation.routes.HomeTabs
import com.clo.accloss.core.modules.profile.presentation.components.ProfileMenu
import kotlinx.coroutines.flow.SharingStarted

object Constants {
    const val APP_VERSION: String = "${BuildConfig.BUILD_TYPE} ${BuildConfig.VERSION_NAME}"

    const val SERVER_ERROR = "Internal Server Error"

    const val DB_ERROR_MESSAGE = "Database is not available"

    const val BASE_URL: String = "https://cloccidental.com"

    const val HUNDRED_DOUBLE: Double = 100.00

    const val HUNDRED_INT: Int = 100

    const val MIN_PAGE: Int = 20

    const val PREFETCH: Int = 10

    // CONFIGURATION KEYS
    const val APPC_UTILIDADES_KEY = "APPC_UTILIDADES"

    private const val STOP_TIME_MILLIS: Long = 5000L

    val SHARING_STARTED = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS)

    val homeTabs: List<Tab> = listOf(
        HomeTabs.Dashboard.tab,
        HomeTabs.Contact.tab,
        HomeTabs.Profile.tab
    )

    val dashboardOptionsMenu = listOf(
        DashboardMenu.Catalogue,
        DashboardMenu.Offers,
    )

    val dashboardStatisticsMenu = listOf(
        DashboardMenu.Managements,
        DashboardMenu.Salesmen,
        DashboardMenu.Customers,
        DashboardMenu.Orders,
        DashboardMenu.Bills,
    )

    val profileMenu = listOf(
        ProfileMenu.Synchronize,
        ProfileMenu.LogOut
    )
}
