package com.clo.accloss.core.presentation.home.presentation.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.vendedor.presentation.screens.VendedorDetailScreen

sealed class DetailsRoutes(val screen: Screen) {
    data class VendedorDetails(val id: String) : DetailsRoutes(
        screen = VendedorDetailScreen(id = id)
    )
}