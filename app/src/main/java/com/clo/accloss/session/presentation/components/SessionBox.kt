package com.clo.accloss.session.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.session.domain.model.Session

@Composable
fun SessionsBody(
    modifier: Modifier = Modifier,
    session: Session,
    sessions: List<Session>,
    onChange: (Session) -> Unit,
    onAdd: (Boolean) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val icon = if (expanded) {
        R.drawable.ic_expand_less_24px
    } else {
        R.drawable.ic_expand_more_24px
    }
    val text = if (expanded) {
        "Ver menos"
    } else {
        "Ver más"
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .background(
                        color = if (!isSystemInDarkTheme()) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        shape = CircleShape
                    )
                    .padding(7.dp)
                    .requiredSize(100.dp),
                painter = painterResource(R.drawable.icon_avlogo),
                contentDescription = session.nombreEmpresa
            )
            Column {
                CustomText(
                    text = session.nombre,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
                CustomText(
                    text = "Gerencia ${session.user}",
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    color = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    ).color,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable {
                    expanded = !expanded
                }
            ) {
                CustomText(
                    text = text
                )
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Sessions Icon"
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                sessions.forEach { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onChange(user)
                            }
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (user.active) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Account"
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account_circle_24px),
                            contentDescription = "Account"
                        )
                        CustomText(
                            text = "${user.nombre}, ${user.nombreEmpresa}",
                            overflow = TextOverflow.Ellipsis,
                            softWrap = true
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAdd(true)
                        }
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_circle_24px),
                        contentDescription = "Account"
                    )
                    CustomText(
                        text = "Agrega una cuenta",
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true
                    )
                }
            }
        }
    }
}
