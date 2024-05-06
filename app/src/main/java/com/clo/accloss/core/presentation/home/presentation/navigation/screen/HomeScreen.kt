package com.clo.accloss.core.presentation.home.presentation.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.app.navigation.routes.AppRoutes
import com.clo.accloss.core.presentation.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.auth.login.presentation.viewmodel.LoginViewModel
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.core.presentation.home.presentation.components.AddAccountDialog
import com.clo.accloss.core.presentation.home.presentation.components.HomeContent
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.core.presentation.home.presentation.viewmodel.HomeViewModel

internal data class HomeScreen(
    var homeRoute: Screen
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val homeViewModel = getScreenModel<HomeViewModel>()
        val state by homeViewModel.state.collectAsState()

        val loginViewModel = getScreenModel<LoginViewModel>()
        val loginState by loginViewModel.state.collectAsState()
        val editEmpresa = loginViewModel.newEmpresa ?: ""
        val editLogin = loginViewModel.newLogin

        var showDialog by remember {
            mutableStateOf(false)
        }

        state.currentSession.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen(it)
                homeViewModel.endSession()
                navigator.replaceAll(
                    AppScreen(
                        initialScreen = AppRoutes.AuthModule().screen
                    )
                )
            },
            onSuccess = { session ->
                Navigator(
                    screen = homeRoute,
                    disposeBehavior = NavigatorDisposeBehavior(disposeNestedNavigators = true),
                    onBackPressed = { _ ->
                        when (homeRoute) {
                            HomeRoutes.DashboardModule.screen -> {
                                true
                            }

                            else -> {
                                navigator.replaceAll(
                                    AppRoutes.HomeModule(
                                        homeRoute = HomeRoutes.DashboardModule.screen
                                    ).screen
                                )
                                true
                            }
                        }
                    }
                ) {
                    if (showDialog) {
                        AddAccountDialog(
                            editEmpresa = editEmpresa,
                            editLogin = editLogin,
                            state = loginState,
                            showChanged = { newValue ->
                                showDialog = newValue
                            },
                            onEvent = { event ->
                                loginViewModel.onEvent(event)
                            }
                        )
                    }

                    HomeContent(
                        homeRoute = homeRoute,
                        currentSession = session,
                        sessions = state.sessions,
                        currentScreen = { CurrentScreen() },
                        onChangeSession = { newSession ->
                            if (!newSession.active) {
                                homeViewModel.onEvent(newSession)
                            }
                        },
                        onAddSession = {
                            showDialog = true
                        },
                        onMenuClick = { item ->
                            val screens = navigator.items.map {
                                it as HomeScreen
                            }
                            when {
                                homeRoute != HomeRoutes.DashboardModule.screen &&
                                    item == HomeRoutes.DashboardModule.screen
                                -> {
                                    navigator.popUntilRoot()
                                }
                                screens.contains(
                                    AppRoutes.HomeModule(
                                        homeRoute = item.screen
                                    ).screen
                                ) -> {
                                    navigator.replace(
                                        AppRoutes.HomeModule(
                                            homeRoute = item.screen
                                        ).screen
                                    )
                                }

                                else -> {
                                    navigator.push(
                                        AppRoutes.HomeModule(
                                            homeRoute = item.screen
                                        ).screen
                                    )
                                }
                            }
                        },
                        onEndSession = {
                            homeViewModel.endSession()
                            navigator.replaceAll(
                                AppRoutes.AuthModule().screen
                            )
                        }
                    )
                }
            },
        )
    }
}
