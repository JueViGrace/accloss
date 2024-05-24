package com.clo.accloss.login.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.modules.app.navigation.screen.AppScreen
import com.clo.accloss.core.modules.home.presentation.navigation.screen.HomeScreen
import com.clo.accloss.login.presentation.components.LoginContent
import com.clo.accloss.login.presentation.events.LoginEvents
import com.clo.accloss.login.presentation.viewmodel.LoginViewModel

class LoginScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<LoginViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val editLogin = viewModel.newLogin
        val editCompany = viewModel.newCompany

        LoginContent(
            editCompany = editCompany,
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
