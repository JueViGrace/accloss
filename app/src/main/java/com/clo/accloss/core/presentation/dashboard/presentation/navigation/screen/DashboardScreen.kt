package com.clo.accloss.core.presentation.dashboard.presentation.navigation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

object DashboardScreen : Screen {
    private fun readResolve(): Any = DashboardScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Text(text = "dashboard")
    }
}
