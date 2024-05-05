package com.clo.accloss.vendedor.presentation.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.CustomText

object VendedorScreen : Screen {
    private fun readResolve(): Any = VendedorScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        CustomText(text = "Vendedor")
    }
}
