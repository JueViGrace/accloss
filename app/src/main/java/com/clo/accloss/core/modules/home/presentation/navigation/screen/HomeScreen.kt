package com.clo.accloss.core.modules.home.presentation.navigation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.clo.accloss.core.modules.app.navigation.screen.AppScreen
import com.clo.accloss.core.modules.home.presentation.navigation.routes.HomeTabs
import com.clo.accloss.core.modules.home.presentation.viewmodel.HomeViewModel
import com.clo.accloss.core.presentation.components.LayoutComponents.BottomNavigationItem
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LoadingComponents.LoadingScreen
import com.clo.accloss.login.presentation.screen.LoginScreen

class HomeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey + super.key

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val homeViewModel = koinScreenModel<HomeViewModel>()
        val state by homeViewModel.state.collectAsStateWithLifecycle()

        state.currentSession.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                navigator.replaceAll(
                    AppScreen(
                        initialScreen = LoginScreen()
                    )
                )
            },
            onSuccess = { _ ->
                TabNavigator(
                    tab = HomeTabs.Dashboard.tab,
                    tabDisposable = { tabNavigator ->
                        TabDisposable(navigator = tabNavigator, tabs = homeTabs)
                    }
                ) {
                    HomeContent(
                        currentScreen = {
                            CurrentTab()
                        },
                    )
                }
            }
        )
    }

    @Composable
    private fun HomeContent(
        currentScreen: @Composable () -> Unit,
    ) {
        DefaultLayoutComponent(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        homeTabs.forEach { tab ->
                            BottomNavigationItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(horizontal = 5.dp),
                                tab = tab,
                            )
                        }
                    },
                    contentPadding = PaddingValues(
                        top = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding(),
                        bottom = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding(),
                    ),
                )
            }
        ) {
            currentScreen()
        }
    }
}
