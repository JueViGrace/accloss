package com.clo.accloss.core.modules.syncronize.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.clo.accloss.core.modules.syncronize.presentation.components.SynchronizeContent
import com.clo.accloss.core.modules.syncronize.presentation.viewmodel.SynchronizeViewModel

object SynchronizeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<SynchronizeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SynchronizeContent(
            state = state,
            onSync = {
                viewModel.synchronize()
            }
        )
    }
}
