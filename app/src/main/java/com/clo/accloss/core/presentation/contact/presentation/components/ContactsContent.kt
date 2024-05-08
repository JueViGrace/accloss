package com.clo.accloss.core.presentation.contact.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.core.presentation.components.ListHeader
import com.clo.accloss.core.presentation.components.PullToRefreshLazyColumn
import com.clo.accloss.vendedor.domain.model.Vendedor

@Composable
fun ContactsContent(
    modifier: Modifier = Modifier,
    vendedores: List<Vendedor>,
    isRefreshing: Boolean,
    onSelect: ((String) -> Unit)? = null,
    onRefresh: () -> Unit
) {
    PullToRefreshLazyColumn(
        modifier = modifier,
        items = vendedores,
        grouped = vendedores.groupBy { it.nombre.first() },
        header = {
            ListHeader(
                modifier = Modifier.padding(5.dp),
                text = it.toString()
            )
        },
        content = { vendedor ->
            ContactsComponent(
                vendedor = vendedor,
                onSelect = {}
            )
        },
        footer = {
            ListFooter(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
            )
        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    )
}
