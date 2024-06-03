package com.clo.accloss.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText

object ListComponents {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun<T> CustomLazyColumn(
        modifier: Modifier = Modifier,
        items: List<T> = emptyList(),
        grouped: Map<String?, List<T>>? = null,
        header: (@Composable () -> Unit)? = null,
        stickyHeader: (@Composable (String?) -> Unit)? = null,
        content: @Composable (T) -> Unit,
        footer: (@Composable () -> Unit)? = null,
        lazyListState: LazyListState = rememberLazyListState(),
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        contentPadding: PaddingValues = PaddingValues(0.dp)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            contentPadding = contentPadding
        ) {
            if (grouped != null) {
                if (header != null) {
                    item {
                        header()
                    }
                }
                grouped.forEach { (initial, contactsForInitial) ->
                    if (stickyHeader != null) {
                        stickyHeader {
                            stickyHeader(initial)
                        }
                    }

                    items(
                        contactsForInitial
                    ) {
                        content(it)
                    }
                }
            } else {
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
            }

            item {
                if (footer != null) {
                    footer()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    @Stable
    fun <T> PullToRefreshLazyColumn(
        modifier: Modifier = Modifier,
        items: List<T>,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        grouped: Map<Char, List<T>>? = null,
        header: (@Composable () -> Unit)? = null,
        stickyHeader: (@Composable (Char?) -> Unit)? = null,
        content: @Composable (T) -> Unit,
        footer: @Composable () -> Unit,
        isRefreshing: Boolean,
        onRefresh: () -> Unit,
        lazyListState: LazyListState = rememberLazyListState()
    ) {
        val pullToRefreshState = rememberPullToRefreshState()

        Box(
            modifier = modifier
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            LazyColumn(
                state = lazyListState,
                contentPadding = contentPadding,
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
            ) {
                if (grouped != null) {
                    if (header != null) {
                        item {
                            header()
                        }
                    }
                    grouped.forEach { (initial, contactsForInitial) ->
                        if (stickyHeader != null) {
                            stickyHeader {
                                stickyHeader(initial)
                            }
                        }

                        items(
                            contactsForInitial
                        ) {
                            content(it)
                        }
                    }
                } else {
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
                }

                item {
                    footer()
                }
            }

            if (pullToRefreshState.isRefreshing) {
                LaunchedEffect(key1 = true) {
                    onRefresh()
                }
            }

            LaunchedEffect(key1 = isRefreshing) {
                if (isRefreshing) {
                    pullToRefreshState.startRefresh()
                } else {
                    pullToRefreshState.endRefresh()
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun ListHeader(
        modifier: Modifier = Modifier,
        @StringRes text: Int
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomText(
                text = stringResource(id = text),
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        }
    }

    @Composable
    fun ListFooter(
        modifier: Modifier = Modifier,
        text: String = ""
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                color = MaterialTheme.colorScheme.onSurface.copy(0.4f)
            )
        }
    }

    @Composable
    fun ListStickyHeader(
        modifier: Modifier = Modifier,
        textModifier: Modifier = Modifier,
        text: String
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            CustomText(
                modifier = textModifier.padding(horizontal = 10.dp),
                text = text,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            )

            HorizontalDivider()
        }
    }
}
