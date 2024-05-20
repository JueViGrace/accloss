package com.clo.accloss.session.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomOutlinedTextField
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.domain.state.RequestState
import com.clo.accloss.session.domain.model.Session

@Composable
fun SessionsDropdown(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    painter: Painter? = null,
    sessions: RequestState<List<Session>>,
    onSessionSelected: (Session) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    var newList: List<Session> by remember {
        mutableStateOf(emptyList())
    }

    sessions.DisplayResult(
        onLoading = {
            LoadingComponent(
                modifier = Modifier.fillMaxWidth(),
            )
        },
        onError = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CustomText(text = "Algo salió mal")
            }
        },
        onSuccess = { list ->
            newList = list
        },
    )

    Box {
        if (newList.isEmpty()) {
            CustomOutlinedTextField(
                modifier = modifier,
                value = "No hay sesiones",
                onValueChanged = { _ ->
                },
                placeholder = { CustomText(text = placeholder) },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        }
                    )
                },
                readOnly = true
            )
        } else {
            CustomOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                value = "Escoge una sesión",
                onValueChanged = { _ ->
                },
                label = { CustomText(text = label) },
                placeholder = { CustomText(text = placeholder) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        expanded = true
                    }
                ),
                leadingIcon = if (painter != null) {
                    {
                        Icon(
                            painter = painter,
                            contentDescription = "",
                        )
                    }
                } else {
                    null
                },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        }
                    )
                },
                readOnly = true
            )

            DropdownMenu(
                modifier = Modifier
                    .width(
                        with(LocalDensity.current) { textFieldSize.width.toDp() }
                    ),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                newList.forEach { session ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.ic_account_circle_24px
                                    ),
                                    contentDescription = "Account"
                                )
                                CustomText(
                                    text = "${session.nombre}, ${session.nombreEmpresa}"
                                )
                            }
                        },
                        onClick = {
                            onSessionSelected(session)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
