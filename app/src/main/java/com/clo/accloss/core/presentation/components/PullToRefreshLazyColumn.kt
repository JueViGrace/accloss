package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

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
