package com.clo.accloss.management.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.common.roundFormat
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
            onClick = { onClick(management.codigo) },
            shape = RoundedCornerShape(5),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    modifier = Modifier.fillMaxWidth(),
                    text = management.nombre,
                    maxLines = 2,
                    softWrap = true,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(text = "${stringResource(R.string.goal)}:")
                    CustomText(text = "$ ${management.meta.roundFormat(0)}")
                    CustomText(text = "|")
                    CustomText(text = "${stringResource(R.string.goal_percentage)}:")
                    CustomText(text = "${management.prcmeta.roundFormat()} %")
                }
            }
        }
    }
}
