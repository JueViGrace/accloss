package com.clo.accloss.login.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.APP_VERSION
import com.clo.accloss.login.domain.model.Login
import com.clo.accloss.login.presentation.events.LoginEvents
import com.clo.accloss.login.presentation.state.LoginState
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.components.TextFieldComponent
import com.clo.accloss.session.presentation.components.SessionsDropdown

@Composable
fun LoginCardComponent(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(5),
    editCompany: String?,
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
        val density = LocalDensity.current

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showSessions,
                    enter = slideInHorizontally { fullWidth ->
                        with(density) {
                            fullWidth.dp.roundToPx() / 2
                        }
                    } + fadeIn(),
                    exit = slideOutHorizontally { fullWidth: Int ->
                        with(density) {
                            fullWidth.dp.roundToPx() / 2
                        }
                    } + fadeOut()
                ) {
                    SignUpWithSession(
                        state = state,
                        visible = showSessions,
                        isVisible = { newValue ->
                            showSessions = newValue
                        },
                        onEvent = onEvent
                    )
                }
            }

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = !showSessions,
                    enter = slideInHorizontally { fullWidth ->
                        with(density) {
                            -fullWidth.dp.roundToPx() / 2
                        }
                    },
                    exit = slideOutHorizontally { fullWidth: Int ->
                        with(density) {
                            -fullWidth.dp.roundToPx() / 2
                        }
                    }
                ) {
                    LoginForm(
                        editCompany = editCompany,
                        editLogin = editLogin,
                        onEvent = onEvent,
                        state = state,
                        isDialog = isDialog,
                        visible = showSessions,
                        isVisible = { newValue ->
                            showSessions = newValue
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginForm(
    editCompany: String?,
    editLogin: Login?,
    onEvent: (LoginEvents) -> Unit,
    state: LoginState,
    isDialog: Boolean,
    visible: Boolean,
    isVisible: (Boolean) -> Unit
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    var focusManager = LocalFocusManager.current

    Column(
        modifier = if (!isDialog) {
            Modifier
                .fillMaxSize()
                .padding(15.dp)
        } else {
            Modifier
                .padding(15.dp)
        },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isDialog) {
            LoginNavigationBox(
                visible = visible,
                isVisible = isVisible
            )
        }
        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            value = editCompany ?: "",
            onChange = { newValue ->
                onEvent(LoginEvents.OnCompanyChanged(newValue))
            },
            label = stringResource(R.string.company),
            leadingIcon = R.drawable.ic_corporate_fare_24px,
            supportingText = state.companyError?.let { stringResource(it) },
            errorStatus = state.companyError != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(LoginEvents.OnCompanyClicked)
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            if (state.loadingCompany) {
                LoadingComponent(
                    modifier = Modifier.size(25.dp)
                )
            } else {
                CustomText(text = stringResource(R.string.validate))
            }
        }

        TextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            value = editLogin?.username ?: "",
            onChange = { newValue ->
                onEvent(LoginEvents.OnUsernameChanged(newValue))
            },
            label = stringResource(R.string.username),
            leadingIcon = R.drawable.ic_account_circle_24px,
            supportingText = state.usernameError?.let { stringResource(it) },
            errorStatus = state.usernameError != null,
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
            label = stringResource(R.string.password),
            leadingIcon = R.drawable.ic_lock_24px,
            trailingIcon = if (passwordVisibility) {
                R.drawable.ic_visibility_off_24px
            } else {
                R.drawable.ic_visibility_24px
            },
            onTrailingIconClick = {
                passwordVisibility = !passwordVisibility
            },
            supportingText = state.passwordError?.let { stringResource(it) },
            errorStatus = state.passwordError != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            enabled = state.canLogin,
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )

        if (state.errorMessage?.isNotEmpty() == true) {
            CustomText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = state.errorMessage,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )
        }

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                focusManager.clearFocus()
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
            if (state.loadingUser) {
                LoadingComponent(
                    modifier = Modifier.size(25.dp)
                )
            } else {
                CustomText(text = stringResource(R.string.sign_in))
            }
        }

        if (!isDialog) {
            CustomText(text = stringResource(R.string.developed_by))
            Image(
                painter = painterResource(R.drawable.ic_clossnegrosss),
                contentDescription = "CLOSS"
            )
            CustomText(text = APP_VERSION)
        }
    }
}

@Composable
fun SignUpWithSession(
    state: LoginState,
    visible: Boolean,
    isVisible: (Boolean) -> Unit,
    onEvent: (LoginEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginNavigationBox(
            visible = visible,
            isVisible = isVisible
        )

        SessionsDropdown(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            sessions = state.sessions,
            label = stringResource(R.string.sessions),
            painter = painterResource(id = R.drawable.ic_account_circle_24px),
            placeholder = stringResource(R.string.select_account),
            onSessionSelected = { newSession ->
                onEvent(LoginEvents.OnSessionSelected(newSession))
            }
        )
    }
}

@Composable
fun LoginNavigationBox(
    visible: Boolean,
    isVisible: (Boolean) -> Unit
) {
    AnimatedVisibility(visible = visible) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        isVisible(!visible)
                    }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(20)
                    )
                    .clip(RoundedCornerShape(20))
                    .padding(10.dp)
                    .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.spacedBy(
                    5.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.ic_left_24px
                    ),
                    contentDescription = stringResource(id = R.string.sign_in)
                )
                CustomText(text = stringResource(id = R.string.sign_in))
            }
        }
    }

    AnimatedVisibility(visible = !visible) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        isVisible(!visible)
                    }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(20)
                    )
                    .clip(RoundedCornerShape(20))
                    .padding(10.dp)
                    .align(Alignment.TopEnd),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(text = stringResource(id = R.string.sessions))
                Icon(
                    painter = painterResource(
                        R.drawable.ic_right_24px
                    ),
                    contentDescription = stringResource(id = R.string.sessions)
                )
            }
        }
    }
}