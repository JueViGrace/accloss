package com.clo.accloss.core.common

import com.clo.accloss.home.presentation.navigation.routes.HomeRoutes

object Constants {
    const val APP_VERSION: String = "1.0.0"

    const val DB_ERROR_MESSAGE = "Database is not available"

    const val BASE_URL: String = "https://cloccidental.com"

    val menuList = listOf(
        HomeRoutes.DashboardModule,
        HomeRoutes.ProductsModule
    )
}
