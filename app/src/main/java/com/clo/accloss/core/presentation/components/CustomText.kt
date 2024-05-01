package com.clo.accloss.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    fontWeight: FontWeight? = MaterialTheme.typography.bodyLarge.fontWeight,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        color = color,
        modifier = modifier,
        textDecoration = textDecoration
    )
}
