package com.clo.accloss.products.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.core.presentation.components.PullToRefreshLazyColumn
import com.clo.accloss.products.domain.model.Product

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    isRefreshing: Boolean,
    onSelect: (String) -> Unit,
    onRefresh: () -> Unit
) {
    PullToRefreshLazyColumn(
        modifier = modifier,
        items = products,
        contentPadding = PaddingValues(5.dp),
        content = { product ->
            AnimatedContent(
                targetState = product,
                label = "Product list",
                transitionSpec = {
                    slideInVertically(
                        initialOffsetY = { height -> height / 4 }
                    ) + fadeIn() togetherWith
                        slideOutVertically(
                            targetOffsetY = { height -> -height / 4 }
                        ) + fadeOut()
                }
            ) {
                ProductListContent(
                    product = it,
                    onSelect = onSelect
                )
            }
        },
        footer = {
            ListFooter(text = stringResource(R.string.end_of_list))
        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    )
}
