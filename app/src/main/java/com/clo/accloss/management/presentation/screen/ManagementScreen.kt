package com.clo.accloss.management.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.management.presentation.components.ManagementsContent
import com.clo.accloss.management.presentation.viewmodel.ManagementViewModel

object ManagementScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ManagementViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        state.managements.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = { message ->
                ErrorScreen(message)
            },
            onSuccess = { list ->
                ManagementsContent(
                    managements = list,
                    onClick = { code ->
                        navigator.push(ManagementDetailsScreen(code))
                    }
                )
            },
        )
    }
}
