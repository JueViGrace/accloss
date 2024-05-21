package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clo.accloss.management.presentation.model.ManagementsUi

@Composable
fun ManagementsContent(
    modifier: Modifier = Modifier,
    managements: List<ManagementsUi>,
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        columns = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        items(managements) { management ->
            ManagementsListComponent(
                modifier = Modifier.defaultMinSize(minHeight = 130.dp, minWidth = 130.dp),
                management = management,
                onClick = onClick
            )
        }
    }
}
