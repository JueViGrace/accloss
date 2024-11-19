package com.clo.accloss.core.common

import cafe.adriel.voyager.navigator.tab.Tab
import com.clo.accloss.BuildConfig
import com.clo.accloss.R
import com.clo.accloss.core.modules.dashboard.presentation.components.DashboardMenu
import com.clo.accloss.core.modules.home.presentation.navigation.routes.HomeTabs
import com.clo.accloss.core.modules.profile.presentation.components.ProfileMenu
import kotlinx.coroutines.flow.SharingStarted

object Constants {
    const val APP_VERSION: String = BuildConfig.VERSION_NAME

    const val INITIAL_DATE = "1000:01:01 00:00:00"

    val SERVER_ERROR = R.string.internal_server_error

    val DB_ERROR_MESSAGE = R.string.database_is_not_available

    val UNEXPECTED_ERROR = R.string.an_unexpected_error_occurred

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
        DashboardMenu.Salesmen,
        DashboardMenu.Customers,
        DashboardMenu.Orders,
        DashboardMenu.Bills,
    )

    val profileMenu = listOf(
        ProfileMenu.Synchronize,
        ProfileMenu.LogOut
    )

    // TODO: TRANSFORM RETURN TO VALUE AND COLOR

    fun calculateDocType(type: String?) = when (type) {
        "1" -> R.string.fac
        "2" -> R.string.n_e
        else -> R.string.not_specified
    }

    fun calculateOrderStatus(status: String) = when (status) {
        "1" -> {
            R.string.waiting_for_approval
        }

        "12" -> {
            R.string.printed
        }

        "17" -> {
            R.string.in_packaging_process
        }

        "20" -> {
            R.string.in_labeling_process
        }

        "25" -> {
            R.string.ready_to_invoice
        }

        "80" -> {
            R.string.invoiced
        }

        "82" -> {
            R.string.waiting_for_dispatch_order
        }

        "85" -> {
            R.string.delivered_to_the_customer
        }

        else -> {
            R.string.not_specified
        }
    }

    fun calculateDocStatus(status: String) = when (status) {
        "0" -> {
            R.string.to_be_paid
        }

        "1" -> {
            R.string.paid_off
        }

        "2" -> {
            R.string.paid
        }

        "3" -> {
            R.string.nullified
        }

        else -> {
            R.string.not_specified
        }
    }
}
