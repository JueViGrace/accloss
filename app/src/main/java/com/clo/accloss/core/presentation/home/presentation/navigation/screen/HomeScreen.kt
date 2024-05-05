package com.clo.accloss.core.presentation.home.presentation.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.app.navigation.routes.AppRoutes
import com.clo.accloss.core.presentation.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.core.presentation.auth.navigation.routes.AuthRoutes
import com.clo.accloss.core.presentation.home.presentation.components.HomeContent
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.core.presentation.home.presentation.viewmodel.HomeViewModel

internal data class HomeScreen(
    val homeRoute: Screen
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<HomeViewModel>()
        val state by viewModel.state.collectAsState()

        state.session.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen(it)
                viewModel.endSession()
                navigator.replaceAll(
                    AppScreen(
                        initialScreen = AppRoutes.AuthModule(
                            AuthRoutes.LoginRoute.screen
                        ).screen
                    )
                )
            },
            onSuccess = {
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
                    HomeContent(
                        homeRoute = homeRoute,
                        currentScreen = { CurrentScreen() },
                        onMenuClick = { item ->
                            val screens = navigator.items.map {
                                it as HomeScreen
                            }
                            when {
                                homeRoute != HomeRoutes.DashboardModule.screen &&
                                    item == HomeRoutes.DashboardModule.screen
                                -> {
                                    navigator.pop()
                                }

                                screens.contains(
                                    AppScreen(
                                        initialScreen = AppRoutes.HomeModule(
                                            homeRoute = item.screen
                                        ).screen
                                    ).initialScreen
                                ) -> {
                                    navigator.replace(
                                        AppScreen(
                                            initialScreen = AppRoutes.HomeModule(
                                                homeRoute = item.screen
                                            ).screen
                                        )
                                    )
                                }

                                else -> {
                                    navigator.push(
                                        AppScreen(
                                            initialScreen = AppRoutes.HomeModule(
                                                homeRoute = item.screen
                                            ).screen
                                        )
                                    )
                                }
                            }
                        },
                        onEndSession = {
                            viewModel.endSession()
                            navigator.replaceAll(
                                AppScreen(
                                    initialScreen = AppRoutes.AuthModule(
                                        AuthRoutes.LoginRoute.screen
                                    ).screen
                                )
                            )
                        }
                    )
                }
            },
        )
    }
}
