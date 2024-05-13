package com.clo.accloss.core.presentation.auth.login.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.clo.accloss.core.presentation.auth.login.domain.model.Login
import com.clo.accloss.core.presentation.auth.login.presentation.events.LoginEvents
import com.clo.accloss.core.presentation.auth.login.presentation.state.LoginState

@Composable
fun AddAccountDialog(
    showChanged: (Boolean) -> Unit,
    editCompany: String?,
    editLogin: Login?,
    onEvent: (LoginEvents) -> Unit,
    state: LoginState
) {
    Dialog(
        onDismissRequest = { showChanged(false) },
    ) {
        LoginCardComponent(
            editCompany = editCompany,
            editLogin = editLogin,
            onEvent = onEvent,
            state = state,
            isDialog = true
        )
    }
}