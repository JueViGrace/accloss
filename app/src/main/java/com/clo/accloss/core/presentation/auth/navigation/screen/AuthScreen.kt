package com.clo.accloss.core.presentation.auth.navigation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import com.clo.accloss.core.presentation.auth.login.presentation.screen.LoginScreen
import com.clo.accloss.core.presentation.components.CustomScreenTransition

class AuthScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Navigator(
            screen = LoginScreen(),
            disposeBehavior = NavigatorDisposeBehavior(
                disposeNestedNavigators = true
            ),
        ) { navigator ->
            CustomScreenTransition(navigator = navigator)
        }
    }
}
