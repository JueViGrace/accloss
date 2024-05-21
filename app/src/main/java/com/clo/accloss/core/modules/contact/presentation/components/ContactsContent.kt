package com.clo.accloss.core.modules.contact.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.core.presentation.components.ListHeader
import com.clo.accloss.core.presentation.components.ListStickyHeader
import com.clo.accloss.core.presentation.components.PullToRefreshLazyColumn
import com.clo.accloss.salesman.domain.model.Salesman
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun ContactsContent(
    salesmen: List<Salesman>,
    isRefreshing: Boolean,
    onSelect: ((String) -> Unit)? = null,
    onRefresh: () -> Unit
) {
    val headers = remember {
        salesmen.map { it.nombre.first().uppercase() }.toSet().toList()
    }
    val listState = rememberLazyListState()

    Row {
        PullToRefreshLazyColumn(
            modifier = Modifier.weight(1f),
            lazyListState = listState,
            items = salesmen,
            grouped = salesmen.groupBy { it.nombre.first() },
            header = {
                ListHeader(text = R.string.salesmen)
            },
            stickyHeader = { char ->
                char?.let {
                    ListStickyHeader(
                        text = it.toString()
                    )
                }
            },
            content = { seller ->
                AnimatedContent(
                    targetState = seller,
                    label = stringResource(R.string.salesman)
                ) {
                    ContactsComponent(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        seller = it,
                        onSelect = { onSelect?.invoke(it) }
                    )
                }
            },
            footer = {
                ListFooter(text = stringResource(R.string.end_of_list))
            },
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        )
        val offsets = remember { mutableStateMapOf<Int, Float>() }
        var selectedHeaderIndex by remember { mutableIntStateOf(0) }
        val scope = rememberCoroutineScope()

        fun updateSelectedIndexIfNeeded(offset: Float) {
            val index = offsets
                .mapValues { abs(it.value - offset) }
                .entries
                .minByOrNull { it.value }
                ?.key ?: return
            if (selectedHeaderIndex == index) return
            selectedHeaderIndex = index
            val selectedItemIndex = salesmen.indexOfFirst {
                it.nombre.first().uppercase() == headers[selectedHeaderIndex]
            }
            scope.launch {
                listState.scrollToItem(
                    index = selectedItemIndex,
                    scrollOffset = offset.toInt()
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .pointerInput(Unit) {
                    detectTapGestures {
                        updateSelectedIndexIfNeeded(it.y)
                    }
                }
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, _ ->
                        updateSelectedIndexIfNeeded(change.position.y)
                    }
                }
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            headers.forEachIndexed { i, header ->
                CustomText(
                    text = header,
                    modifier = Modifier.onGloballyPositioned {
                        offsets[i] = it.boundsInParent().center.y
                    }
                )
            }
        }
    }
}
