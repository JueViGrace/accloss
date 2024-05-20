package com.clo.accloss.core.modules.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import cafe.adriel.voyager.navigator.Navigator
import com.clo.accloss.core.modules.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.components.CustomScreenTransition
import com.clo.accloss.core.modules.home.presentation.navigation.screen.HomeScreen
import com.clo.accloss.core.presentation.theme.ACCLOSSTheme

@Composable
fun App() {
    ACCLOSSTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(
                LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current
            ) {
                Navigator(
                    screen = AppScreen(
                        initialScreen = HomeScreen()
                    ),
                ) { navigator ->
                    CustomScreenTransition(navigator = navigator)
                }
            }
        }
    }
}
