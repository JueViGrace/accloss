package com.clo.accloss.core.presentation.home.presentation.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.clo.accloss.core.common.Constants.homeTabs
import com.clo.accloss.core.presentation.app.navigation.screen.AppScreen
import com.clo.accloss.core.presentation.auth.navigation.screen.AuthScreen
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.core.presentation.home.presentation.components.HomeContent
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeTabs
import com.clo.accloss.core.presentation.home.presentation.viewmodel.HomeViewModel

class HomeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val homeViewModel = koinScreenModel<HomeViewModel>()
        val state by homeViewModel.state.collectAsState()

        state.currentSession.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                navigator.replaceAll(
                    AppScreen(
                        initialScreen = AuthScreen()
                    )
                )
            },
            onSuccess = { session ->
                TabNavigator(
                    tab = HomeTabs.Dashboard.tab,
                    tabDisposable = { tabNavigator ->
                        TabDisposable(navigator = tabNavigator, tabs = homeTabs)
                    },
                    key = key
                ) { _ ->
                    HomeContent(
                        currentScreen = {
                            CurrentTab()
                        },
                    )
                }
            }
        )
    }
}
