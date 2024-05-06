package com.clo.accloss.session.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.session.domain.model.Session

@Composable
fun SessionBox(
    modifier: Modifier = Modifier,
    session: Session,
    onShowSessions: (Boolean) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val icon = if (expanded) {
        R.drawable.ic_expand_less_24px
    } else {
        R.drawable.ic_expand_more_24px
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                    onShowSessions(expanded)
                }
                .fillMaxWidth()
                .padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = R.drawable.icon_avlogo),
                contentDescription = "Nav Icon"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(
                    text = "${session.nombre}, ${session.user}",
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )

                Icon(painter = painterResource(id = icon), contentDescription = "Sessions Icon")
            }
        }
    }
}
