package com.clo.accloss.home.presentation.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.dashboard.presentation.screen.DashboardScreen
import com.clo.accloss.products.presentation.screen.ProductsScreen

sealed class HomeRoutes(
    val screen: Screen,
    val title: String,
//    val icon: DrawableResource? = null
) {
    data object DashboardModule :
        HomeRoutes(
            screen = DashboardScreen,
            title = "Home",
//        icon = Res.drawable.ic_home_app_logo_24px
        )

    data object ProductsModule :
        HomeRoutes(
            screen = ProductsScreen,
            title = "Artículos",
//        icon = Res.drawable.ic_inventory_2_24px
        )
}
