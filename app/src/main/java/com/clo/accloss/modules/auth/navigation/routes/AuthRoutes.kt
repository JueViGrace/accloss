package com.clo.accloss.modules.auth.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.modules.auth.login.presentation.screen.LoginScreen

sealed class AuthRoutes(val screen: Screen) {
    data object LoginRoute : AuthRoutes(screen = LoginScreen)
}
