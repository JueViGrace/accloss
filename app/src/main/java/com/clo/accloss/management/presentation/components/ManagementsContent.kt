package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomLazyColumn
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.core.presentation.components.ListHeader
import com.clo.accloss.management.presentation.model.ManagementsUi

@Composable
fun ManagementsContent(
    managements: List<ManagementsUi>,
    onClick: (String) -> Unit
) {
    CustomLazyColumn(
        items = managements,
        header = {
            ListHeader(text = R.string.managements)
        },
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
