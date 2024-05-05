package com.clo.accloss.core.presentation.app.navigation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator

internal data class AppScreen(
    val initialScreen: Screen
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Navigator(
            screen = initialScreen,
        )
    }
}
