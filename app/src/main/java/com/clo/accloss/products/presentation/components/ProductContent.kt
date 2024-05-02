package com.clo.accloss.products.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.components.CustomText
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
            CustomText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                text = "Fin de la lista",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                color = MaterialTheme.typography.bodySmall.color
            )
        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    )
}
