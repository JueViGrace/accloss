package com.clo.accloss.core.common

import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeRoutes

object Constants {
    const val APP_VERSION: String = "1.0.0"

    const val SERVER_ERROR = "Internal Server Error"

    const val DB_ERROR_MESSAGE = "Database is not available"

    const val BASE_URL: String = "https://cloccidental.com"

    const val HUNDRED_DOUBLE: Double = 100.00

    const val MIN_PAGE: Int = 20

    const val PREFETCH: Int = 10

    val menuList = listOf(
        HomeRoutes.DashboardModule,
        HomeRoutes.ProductsModule,

    )
}
