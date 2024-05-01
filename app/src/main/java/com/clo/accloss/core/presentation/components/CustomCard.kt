package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CustomClickableCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(5),
    colors: CardColors = cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ),
    elevation: CardElevation = CardDefaults.cardElevation(
        defaultElevation = 2.dp
    ),
    border: BorderStroke? = BorderStroke(
        width = 0.5.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    ),
    content: @Composable (ColumnScope.() -> Unit)
) {
    if (onClick != null) {
        Card(
            modifier = modifier,
            onClick = onClick,
            enabled = enabled,
            shape = shape,
            elevation = elevation,
            colors = colors,
            border = border,
            content = content
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            elevation = elevation,
            colors = colors,
            border = border,
            content = content
        )
    }
}
