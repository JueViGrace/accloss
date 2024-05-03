package com.clo.accloss.modules.app.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.modules.auth.navigation.routes.AuthRoutes
import com.clo.accloss.modules.auth.navigation.screen.AuthScreen
import com.clo.accloss.modules.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.modules.home.presentation.navigation.screen.HomeScreen

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
