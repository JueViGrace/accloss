package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun<T> CustomLazyColumn(
    modifier: Modifier = Modifier,
    items: List<T>,
    header: (@Composable () -> Unit)? = null,
    content: @Composable (T) -> Unit,
    footer: (@Composable () -> Unit)? = null,
    lazyListState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top)
    ) {
        if (header != null) {
            item {
                header()
            }
        }

        items(
            items
        ) {
            content(it)
        }

        item {
            if (footer != null) {
                footer()
            }
        }
    }
}
