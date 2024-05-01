package com.clo.accloss.products.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

object ProductsScreen : Screen {
    private fun readResolve(): Any = ProductsScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Text(text = "products")
    }
}
