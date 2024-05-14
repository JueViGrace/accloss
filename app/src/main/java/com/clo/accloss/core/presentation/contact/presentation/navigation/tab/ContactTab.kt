package com.clo.accloss.core.presentation.contact.presentation.navigation.tab

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
import com.clo.accloss.core.presentation.contact.presentation.navigation.screen.ContactScreen
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeTabs

object ContactTab : Tab {
    private fun readResolve(): Any = ContactTab

    override val key: ScreenKey = uniqueScreenKey

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = HomeTabs.Contact.icon)
            val title = stringResource(id = HomeTabs.Contact.title)
            return remember {
                TabOptions(
                    index = HomeTabs.Contact.index,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(
            screen = ContactScreen()
        ) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
