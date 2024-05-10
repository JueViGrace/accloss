package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

@Composable
fun RowScope.BottomNavigationItem(
    modifier: Modifier = Modifier,
    tab: Tab,
) {
    val tabNavigator = LocalTabNavigator.current
    val selected = tabNavigator.current.key == tab.key

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                tabNavigator.current = tab
            },
        ) {
            tab.options.icon?.let { icon ->
                Icon(
                    modifier = Modifier.size(35.dp),
                    painter = icon,
                    contentDescription = tab.options.title,
                    tint = if (selected) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    }
                )
            }
        }
        if (selected) {
            CustomText(
                text = tab.options.title,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
