package com.clo.accloss.products.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.core.presentation.components.PullToRefreshLazyColumn
import com.clo.accloss.products.domain.model.Product

@Composable
fun ProductContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    isRefreshing: Boolean,
    onSelect: ((String) -> Unit)? = null,
    onRefresh: () -> Unit
) {
    PullToRefreshLazyColumn(
        modifier = modifier,
        items = products,
        content = { product ->
            ProductListContent(product = product) {
                onSelect?.invoke(product.codigo)
            }
        },
        footer = {
            ListFooter()
        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    )
}
