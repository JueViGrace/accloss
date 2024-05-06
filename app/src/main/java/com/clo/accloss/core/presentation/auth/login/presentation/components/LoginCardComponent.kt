package com.clo.accloss.core.presentation.auth.login.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.APP_VERSION
import com.clo.accloss.core.presentation.auth.login.domain.model.Login
import com.clo.accloss.core.presentation.auth.login.presentation.events.LoginEvents
import com.clo.accloss.core.presentation.auth.login.presentation.state.LoginState
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.components.TextFieldComponent
import com.clo.accloss.session.presentation.components.SessionsDropdown

@Composable
fun LoginCardComponent(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(5),
    editEmpresa: String?,
    editLogin: Login?,
    onEvent: (LoginEvents) -> Unit,
    state: LoginState,
    isDialog: Boolean = false
) {
    var showSessions by remember {
        mutableStateOf(false)
    }

    CustomClickableCard(
        modifier = modifier,
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                showSessions = !showSessions
                            }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(20)
                            )
                            .clip(RoundedCornerShape(20))
                            .padding(10.dp)
                            .align(
                                if (showSessions) {
                                    Alignment.TopStart
                                } else {
                                    Alignment.TopEnd
                                }
                            ),
                        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (showSessions) {
                            Icon(
                                painter = painterResource(
                                    R.drawable.ic_left_24px
                                ),
                                contentDescription = "Sessions"
                            )

                            CustomText(
                                text = "Log In",
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        } else {
                            CustomText(
                                text = "Sesiones",
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                            Icon(
                                painter = painterResource(
                                    R.drawable.ic_right_24px
                                ),
                                contentDescription = "Sessions"
                            )
                        }
                    }
                }
            }
            if (showSessions) {
                SessionsDropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    sessions = state.sessions,
                    label = "Sesiones",
                    painter = painterResource(id = R.drawable.ic_account_circle_24px),
                    placeholder = "Seleccione una sesion",
                    onSessionSelected = { newSession ->
                        onEvent(LoginEvents.OnSessionSelected(newSession))
                    }
                )
            } else {
                TextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = editEmpresa ?: "",
                    onChange = { newValue ->
                        onEvent(LoginEvents.OnEmpresaChanged(newValue))
                    },
                    label = "Empresa",
                    icon = R.drawable.ic_corporate_fare_24px,
                    supportingText = state.empresaError,
                    errorStatus = state.empresaError?.isNotEmpty() ?: false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                if (state.loadingEmpresa) {
                    LoadingComponent()
                } else {
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onEvent(LoginEvents.OnEmpresaClicked)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        CustomText(text = "Validar")
                    }
                }

                TextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = editLogin?.username ?: "",
                    onChange = { newValue ->
                        onEvent(LoginEvents.OnUsernameChanged(newValue))
                    },
                    label = "Username",
                    icon = R.drawable.ic_account_circle_24px,
                    supportingText = state.usernameError,
                    errorStatus = state.usernameError?.isNotEmpty() == true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    enabled = state.canLogin
                )

                TextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = editLogin?.password ?: "",
                    onChange = { newValue ->
                        onEvent(LoginEvents.OnPasswordChanged(newValue))
                    },
                    label = "Password",
                    icon = R.drawable.ic_lock_24px,
                    supportingText = state.passwordError,
                    errorStatus = state.passwordError?.isNotEmpty() == true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    enabled = state.canLogin,
                    visualTransformation = PasswordVisualTransformation()
                )

                if (state.loadingUser) {
                    LoadingComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                } else {
                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        onClick = {
                            onEvent(LoginEvents.OnLoginClicked)
                        },
                        enabled = editLogin?.username?.isNotEmpty() == true && editLogin.password.isNotEmpty(),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            disabledContainerColor =
                            MaterialTheme
                                .colorScheme
                                .onPrimaryContainer
                                .copy(
                                    alpha = 0.4f
                                )
                        )
                    ) {
                        CustomText(
                            text = "Log in"
                        )
                    }
                }

                if (state.errorMessage?.isNotEmpty() == true) {
                    CustomText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = state.errorMessage,
                        textAlign = TextAlign.Center
                    )
                }

                if (!isDialog) {
                    CustomText(text = "Desarrollado por:")
                    Image(
                        painter = painterResource(R.drawable.ic_clossnegrosss),
                        contentDescription = "CLOSS"
                    )
                    CustomText(text = "Ver. $APP_VERSION")
                }
            }
        }
    }
}
