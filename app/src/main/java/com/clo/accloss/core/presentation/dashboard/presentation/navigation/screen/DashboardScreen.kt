package com.clo.accloss.core.presentation.dashboard.presentation.navigation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.dashboard.presentation.viewmodel.DashboardViewModel

object DashboardScreen : Screen {
    private fun readResolve(): Any = DashboardScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<DashboardViewModel>()
        val state by viewModel.state.collectAsState()
    }
}
