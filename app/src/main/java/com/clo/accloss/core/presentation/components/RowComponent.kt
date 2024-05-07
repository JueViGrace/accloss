package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun RowComponent(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(5.dp, Alignment.Start),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    icon: Painter? = null,
    field: String = "",
    value: String = ""
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        if (icon != null) { Icon(painter = icon, contentDescription = "Icon", modifier = Modifier.size(20.dp)) }

        when {
            field.isNotEmpty() && value.isNotEmpty() -> {
                CustomText(text = field)
                CustomText(text = value)
            }
            field.isNotEmpty() -> {
                CustomText(text = field)
            }
            value.isNotEmpty() -> {
                CustomText(text = value)
            }
        }
    }
}
