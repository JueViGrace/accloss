package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.management.presentation.state.ManagementDetailsState

@Composable
fun ManagementContent(
    modifier: Modifier = Modifier,
    state: ManagementDetailsState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        state.management.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen(it)
            },
            onSuccess = { management ->
                CustomText(text = management.codigo)
            },
        )
    }
}
