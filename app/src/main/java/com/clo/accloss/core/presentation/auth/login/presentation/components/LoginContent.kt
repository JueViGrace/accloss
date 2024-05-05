package com.clo.accloss.core.presentation.auth.login.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.presentation.auth.login.presentation.events.LoginEvents
import com.clo.accloss.core.presentation.auth.login.presentation.state.LoginState
import com.clo.accloss.R
import com.clo.accloss.core.presentation.auth.login.domain.model.Login
import com.clo.accloss.core.common.Constants
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.components.TextFieldComponent

@Composable
fun LoginContent(
    editEmpresa: String?,
    editLogin: Login?,
    state: LoginState,
    onEvent: (LoginEvents) -> Unit,
    requestError: String? = null
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.login_background),
                contentScale = ContentScale.FillBounds,
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.requiredWidth(250.dp),
                painter = painterResource(R.drawable.ic_login),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Login icon"
            )

            BoxWithConstraints(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                CustomClickableCard(
                    modifier = Modifier.width(width = if (maxWidth < 400.dp) maxWidth else 400.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                        ) {
                            TextFieldComponent(
                                modifier = Modifier.weight(0.7f),
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
                                LoadingComponent(
                                    modifier = Modifier.weight(0.3f)
                                )
                            } else {
                                ElevatedButton(
                                    modifier = Modifier.weight(0.3f),
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
                                modifier = Modifier.fillMaxWidth()
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
                                enabled = editLogin?.username?.isNotEmpty() == true &&
                                    editLogin.password.isNotEmpty(),
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
                                modifier = Modifier.fillMaxWidth()
                                    .padding(10.dp),
                                text = state.errorMessage,
                                textAlign = TextAlign.Center
                            )
                        }

                        if (requestError != null) {
                            CustomText(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(10.dp),
                                text = requestError,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CustomClickableCard(
                    shape = RoundedCornerShape(15)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomText(text = "Desarrollado por:")
                        Image(
                            painter = painterResource(R.drawable.ic_clossnegrosss),
                            contentDescription = "CLOSS"
                        )
                        CustomText(text = "Ver. ${Constants.APP_VERSION}")
                    }
                }
            }
        }
    }
}
