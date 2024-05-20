package com.clo.accloss.management.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.management.presentation.components.ManagementContent
import com.clo.accloss.management.presentation.viewmodel.ManagementDetailsViewModel
import org.koin.core.parameter.parametersOf

data class ManagementDetailsScreen(
    val code: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey + code

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ManagementDetailsViewModel>(parameters = { parametersOf(code) })
        val state by viewModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                DefaultTopBar(title = {
                                      CustomText(text = code)
                },
                    navigationIcon = {
                        DefaultBackArrow {
                            navigator.pop()
                        }
                })
            }
        ){ innerPadding ->
            ManagementContent(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                state = state
            )
        }
    }
}
