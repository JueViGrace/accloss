package com.clo.accloss.core.presentation.auth.login.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.auth.login.presentation.components.LoginContent
import com.clo.accloss.core.presentation.auth.login.presentation.events.LoginEvents
import com.clo.accloss.core.presentation.auth.login.presentation.viewmodel.LoginViewModel
import com.clo.accloss.core.presentation.home.presentation.navigation.screen.HomeScreen

class LoginScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()
        val editLogin = viewModel.newLogin
        val editEmpresa = viewModel.newEmpresa

        LoginContent(
            editEmpresa = editEmpresa,
            editLogin = editLogin,
            state = state,
            onEvent = viewModel::onEvent,
            onNavigate = {
                navigator.replaceAll(
                    AppScreen(
                        initialScreen = HomeScreen()
                    )
                )
                viewModel.onEvent(LoginEvents.OnLoginDismiss)
            }
        )
    }
}
