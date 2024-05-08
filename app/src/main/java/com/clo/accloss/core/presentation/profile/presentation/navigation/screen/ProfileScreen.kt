package com.clo.accloss.core.presentation.profile.presentation.navigation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.presentation.auth.login.presentation.viewmodel.LoginViewModel
import com.clo.accloss.core.presentation.components.AddAccountDialog
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponent
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.profile.presentation.viewmodel.ProfileViewModel
import com.clo.accloss.session.presentation.components.SessionsBody

class ProfileScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProfileViewModel>()
        val state by viewModel.state.collectAsState()
        val newSession = viewModel.newSession

        var showDialog by remember {
            mutableStateOf(false)
        }

        val loginViewModel = koinScreenModel<LoginViewModel>()
        val loginState by loginViewModel.state.collectAsState()

        newSession?.let { session ->

            if (showDialog) {
                AddAccountDialog(
                    showChanged = { newValue ->
                        showDialog = newValue
                    },
                    editEmpresa = loginViewModel.newEmpresa,
                    editLogin = loginViewModel.newLogin,
                    onEvent = loginViewModel::onEvent,
                    state = loginState,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.sessions.DisplayResult(
                    onLoading = { LoadingComponent() },
                    onError = {
                        ErrorComponent(it)
                    },
                    onSuccess = { list ->
                        SessionsBody(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            session = session,
                            sessions = list,
                            onChange = { changeSession ->
                                if (!changeSession.active) {
                                    viewModel.changeSession(changeSession)
                                }
                            },
                            onAdd = { newValue ->
                                showDialog = newValue
                            }
                        )
                    },
                )

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomText(text = "TODO: Estadisticas generales")
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // TODO: NAVIGATE TO PROMOCIONES
                        }
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start)
                ) {
                    Icon(painter = painterResource(R.drawable.ic_shopping_bag_24px), contentDescription = "Offers")
                    CustomText(
                        text = "Promociones",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // TODO: NAVIGATE TO ESTADISTICAS
                        }
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start)
                ) {
                    Icon(painter = painterResource(R.drawable.ic_shopping_bag_24px), contentDescription = "Offers")
                    CustomText(
                        text = "Estadísticas",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.endSession()
                        }
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = "Log out",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(painter = painterResource(id = R.drawable.ic_logout_24px), contentDescription = "Log out")
                }
            }
        }
    }
}
