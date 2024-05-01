package com.clo.accloss.auth.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.auth.login.presentation.screen.LoginScreen

sealed class AuthRoutes(val screen: Screen) {
    data object LoginRoute : AuthRoutes(screen = LoginScreen)
}
