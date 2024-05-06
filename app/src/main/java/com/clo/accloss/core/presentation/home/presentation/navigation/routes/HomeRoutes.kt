package com.clo.accloss.core.presentation.home.presentation.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.R
import com.clo.accloss.core.presentation.dashboard.presentation.navigation.screen.DashboardScreen
import com.clo.accloss.products.presentation.screen.ProductsScreen
import com.clo.accloss.vendedor.presentation.screens.VendedorScreen

sealed class HomeRoutes(
    val screen: Screen,
    val title: String,
    val icon: Int? = null
) {
    data object DashboardModule :
        HomeRoutes(
            screen = DashboardScreen,
            title = "Home",
            icon = R.drawable.ic_home_app_logo_24px
        )

    data object ProductsModule :
        HomeRoutes(
            screen = ProductsScreen,
            title = "Artículos",
            icon = R.drawable.ic_inventory_2_24px
        )

    data object VendedorModule :
        HomeRoutes(
            screen = VendedorScreen,
            title = "Vendedores",
            icon = R.drawable.ic_group_24px
        )
}
