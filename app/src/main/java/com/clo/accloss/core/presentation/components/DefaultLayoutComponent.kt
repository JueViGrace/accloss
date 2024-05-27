package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.clo.accloss.core.domain.state.RequestState

@Composable
fun<T> DefaultLayoutComponent(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    state: RequestState<T>,
    content: @Composable (T) -> Unit
) {
    Scaffold(
        topBar = topBar
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            state.DisplayResult(
                onLoading = {
                    LoadingScreen()
                },
                onError = { message ->
                    ErrorScreen(message)
                },
                onSuccess = { data ->
                    content(data)
                },
            )
        }
    }
}
