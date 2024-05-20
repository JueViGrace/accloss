package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.components.CustomLazyColumn
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.management.presentation.model.ManagementsUi

@Composable
fun ManagementsContent(
    modifier: Modifier = Modifier,
    managements: List<ManagementsUi>,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        CustomLazyColumn(
            items = managements,
            content = { management ->
                ManagementsListComponent(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    management = management,
                    onClick = onClick
                )
            },
            footer = { ListFooter() },
        )
    }
}
