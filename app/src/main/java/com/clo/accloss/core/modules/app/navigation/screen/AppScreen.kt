package com.clo.accloss.core.modules.app.navigation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior

internal data class AppScreen(
    val initialScreen: Screen
) : Screen {
    override val key: ScreenKey = uniqueScreenKey + super.key

    @Composable
    override fun Content() {
        Navigator(
            screen = initialScreen,
            disposeBehavior = NavigatorDisposeBehavior(
                disposeNestedNavigators = true
            ),
        )
    }
}
