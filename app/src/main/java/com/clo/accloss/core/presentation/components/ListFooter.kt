package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.clo.accloss.R

@Composable
fun ListFooter(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CustomText(
            text = stringResource(R.string.end_of_list),
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
            color = MaterialTheme.typography.bodySmall.color
        )
    }
}
