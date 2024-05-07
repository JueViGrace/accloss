package com.clo.accloss.core.presentation.profile.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.clo.accloss.core.presentation.dashboard.presentation.navigation.tab.DashboardTab
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeTabs
import com.clo.accloss.core.presentation.profile.presentation.navigation.screen.ProfileScreen
import kotlin.random.Random

object ProfileTab : Tab {
    private fun readResolve(): Any = DashboardTab

    override val key: ScreenKey = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Profile.icon)
            return remember {
                TabOptions(
                    index = HomeTabs.Profile.index,
                    title = HomeTabs.Profile.title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = ProfileScreen(),
            key = key
        ) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
