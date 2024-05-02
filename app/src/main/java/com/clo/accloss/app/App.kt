package com.clo.accloss.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.clo.accloss.app.navigation.routes.AppRoutes
import com.clo.accloss.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.theme.ACCLOSSTheme

@Composable
fun App() {
    ACCLOSSTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(
                screen = AppScreen(
                    initialScreen = AppRoutes.HomeModule().screen
                )
            ) { navigator ->
                SlideTransition(navigator = navigator)
            }
        }
    }
}
