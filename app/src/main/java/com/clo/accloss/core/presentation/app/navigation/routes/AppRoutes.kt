package com.clo.accloss.core.presentation.app.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.core.presentation.auth.navigation.routes.AuthRoutes
import com.clo.accloss.core.presentation.auth.navigation.screen.AuthScreen
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.core.presentation.home.presentation.navigation.screen.HomeScreen

sealed class AppRoutes(val screen: Screen) {
    data class AuthModule(
        val authRoute: Screen = AuthRoutes.LoginRoute.screen
    ) : AppRoutes(
        screen = AuthScreen(authRoute)
    )
    data class HomeModule(
        val homeRoute: Screen = HomeRoutes.DashboardModule.screen
    ) : AppRoutes(
        screen = HomeScreen(homeRoute)
    )
}
