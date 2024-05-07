package com.clo.accloss.core.presentation.dashboard.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.clo.accloss.core.presentation.dashboard.presentation.navigation.screen.DashboardScreen
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeTabs
import kotlin.random.Random

object DashboardTab : Tab {
    private fun readResolve(): Any = DashboardTab

    override val key: ScreenKey = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Dashboard.icon)
            return remember {
                TabOptions(
                    index = HomeTabs.Dashboard.index,
                    title = HomeTabs.Dashboard.title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = DashboardScreen(),
            key = key
        ) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
