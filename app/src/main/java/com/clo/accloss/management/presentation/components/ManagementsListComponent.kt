package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.management.presentation.model.ManagementsUi

@Composable
fun ManagementsListComponent(
    modifier: Modifier = Modifier,
    management: ManagementsUi,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CustomClickableCard(
            modifier = Modifier.matchParentSize(),
            onClick = { onClick(management.codigo) },
            shape = RoundedCornerShape(10),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    text = management.nombre,
                    maxLines = 10,
                    softWrap = true,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
