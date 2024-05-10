package com.clo.accloss.core.presentation.auth.login.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clo.accloss.R
import com.clo.accloss.core.presentation.auth.login.domain.model.Login
import com.clo.accloss.core.presentation.auth.login.presentation.events.LoginEvents
import com.clo.accloss.core.presentation.auth.login.presentation.state.LoginState

@Composable
fun LoginContent(
    editCompany: String?,
    editLogin: Login?,
    state: LoginState,
    onEvent: (LoginEvents) -> Unit,
    onNavigate: () -> Unit
) {
    val session = if (state.currentSession.isSuccess()) state.currentSession.getSuccessData() else null

    session?.let {
        if (it.active) {
            onNavigate()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.login_background),
                contentScale = ContentScale.FillBounds,
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(R.drawable.ic_login),
            contentScale = ContentScale.Fit,
            contentDescription = "Login icon"
        )

        LoginCardComponent(
            modifier = Modifier.fillMaxHeight(),
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            editCompany = editCompany ?: "",
            editLogin = editLogin,
            onEvent = onEvent,
            state = state,
        )
    }
}
