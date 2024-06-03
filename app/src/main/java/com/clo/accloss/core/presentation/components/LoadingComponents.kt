package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object LoadingComponents {
    @Composable
    fun LoadingComponent(
        modifier: Modifier = Modifier,
        strokeWith: Dp = ProgressIndicatorDefaults.CircularStrokeWidth
    ) {
        Box(
            modifier = modifier.size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = strokeWith
            )
        }
    }

    @Composable
    fun LoadingScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
