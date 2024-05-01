package com.clo.accloss.home.presentation.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.app.navigation.routes.AppRoutes
import com.clo.accloss.app.navigation.screen.AppScreen
import com.clo.accloss.auth.navigation.routes.AuthRoutes
import com.clo.accloss.core.state.RequestState
import com.clo.accloss.home.presentation.components.HomeContent
import com.clo.accloss.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.home.presentation.viewmodel.HomeViewModel

internal data class HomeScreen(
    val homeRoute: Screen
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<HomeViewModel>()
        val state by viewModel.state.collectAsState()

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
                                AppRoutes.HomeModule(
                                    homeRoute = item.screen
                                ).screen

                            )
                        }
                    }
                },
                onEndSession = {
                    when (state.session) {
                        is RequestState.Success -> {
                            viewModel.endSession()
                            navigator.replaceAll(AppRoutes.AuthModule(AuthRoutes.LoginRoute.screen).screen)
                        }
                        else -> {
                            viewModel.endSession()
                            navigator.replaceAll(AppRoutes.AuthModule(AuthRoutes.LoginRoute.screen).screen)
                        }
                    }
                }
            )
        }
    }
}
