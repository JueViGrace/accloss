package com.clo.accloss.core.presentation.contact.presentation.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.clo.accloss.core.presentation.contact.presentation.navigation.screen.ContactScreen
import com.clo.accloss.core.presentation.dashboard.presentation.navigation.tab.DashboardTab
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeTabs

object ContactTab : Tab {
    private fun readResolve(): Any = DashboardTab

    override val key: ScreenKey = uniqueScreenKey

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Contact.icon)
            return remember {
                TabOptions(
                    index = HomeTabs.Contact.index,
                    title = HomeTabs.Contact.title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = ContactScreen(),
            key = key
        ) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
