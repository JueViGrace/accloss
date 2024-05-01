package com.clo.accloss.app.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.auth.navigation.routes.AuthRoutes
import com.clo.accloss.auth.navigation.screen.AuthScreen
import com.clo.accloss.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.home.presentation.navigation.screen.HomeScreen

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
