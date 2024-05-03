package com.clo.accloss.modules.auth.login.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.modules.app.navigation.routes.AppRoutes
import com.clo.accloss.modules.app.navigation.screen.AppScreen
import com.clo.accloss.modules.auth.login.presentation.components.LoginContent
import com.clo.accloss.modules.auth.login.presentation.events.LoginEvents
import com.clo.accloss.modules.auth.login.presentation.viewmodel.LoginViewModel

object LoginScreen : Screen {
    private fun readResolve(): Any = LoginScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()
        val editLogin = viewModel.newLogin
        val editEmpresa = viewModel.newEmpresa

        state.session.DisplayResult(
            onIdle = {
                LoginContent(
                    editEmpresa = editEmpresa,
                    editLogin = editLogin,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            },
            onLoading = { LoadingScreen() },
            onError = {
                LoginContent(
                    editEmpresa = editEmpresa,
                    editLogin = editLogin,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            },
            onSuccess = { session ->
                if (session.active) {
                    navigator.replace(
                        AppScreen(
                            initialScreen = AppRoutes.HomeModule().screen
                        )
                    )
                    viewModel.onEvent(LoginEvents.OnLoginDismiss)
                }
            }
        )
    }
}
