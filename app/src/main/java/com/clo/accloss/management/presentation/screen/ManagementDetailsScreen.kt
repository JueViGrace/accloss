package com.clo.accloss.management.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.management.presentation.viewmodel.ManagementDetailsViewModel
import org.koin.core.parameter.parametersOf

data class ManagementDetailsScreen(
    val code: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey + code

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<ManagementDetailsViewModel>(parameters = { parametersOf(code) })
        val state by viewModel.state.collectAsStateWithLifecycle()

        state.management.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = { message ->
                ErrorScreen(message)
            },
            onSuccess = { management ->
                CustomText(text = management.codigo)
            },
        )
    }
}
