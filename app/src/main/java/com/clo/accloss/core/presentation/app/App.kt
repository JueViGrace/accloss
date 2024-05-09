package com.clo.accloss.core.presentation.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.clo.accloss.core.presentation.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.components.CustomScreenTransition
import com.clo.accloss.core.presentation.home.presentation.navigation.screen.HomeScreen
import com.clo.accloss.core.presentation.theme.ACCLOSSTheme

@Composable
fun App() {
    ACCLOSSTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
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
