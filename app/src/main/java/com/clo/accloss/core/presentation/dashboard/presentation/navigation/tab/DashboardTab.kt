package com.clo.accloss.core.presentation.dashboard.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.clo.accloss.core.presentation.dashboard.presentation.navigation.screen.DashboardScreen
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeTabs

object DashboardTab : Tab {
    private fun readResolve(): Any = DashboardTab

    override val key: ScreenKey = uniqueScreenKey

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Dashboard.icon)
            val title = stringResource(HomeTabs.Dashboard.title)
            return remember {
                TabOptions(
                    index = HomeTabs.Dashboard.index,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = DashboardScreen(),
        ) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
