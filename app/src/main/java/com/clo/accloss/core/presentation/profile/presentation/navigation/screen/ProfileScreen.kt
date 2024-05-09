package com.clo.accloss.core.presentation.profile.presentation.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.common.Constants.profileMenu
import com.clo.accloss.core.presentation.auth.login.presentation.viewmodel.LoginViewModel
import com.clo.accloss.core.presentation.components.AddAccountDialog
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponent
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.profile.presentation.components.ProfileMenu
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
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    CustomText(
                        text = "Finanzas Gerencia: ${session.user}",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        15.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(25)
                            )
                            .clip(RoundedCornerShape(25)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(text = "Deudas",)
                            CustomText(
                                text = "$ 2312312",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(25)
                            )
                            .clip(RoundedCornerShape(25)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(text = "Vencido",)
                            CustomText(
                                text = "$ 23124213",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(25)
                            )
                            .clip(RoundedCornerShape(25)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(text = "Cobrado",)
                            CustomText(
                                text = "$ 12421124",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                            )
                        }
                    }
                }

                CustomClickableCard(
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        columns = GridCells.Adaptive(100.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                    ) {
                        items(
                            items = profileMenu,
                            key = { item -> item.name }
                        ) { menu ->
                            Box {
                                CustomClickableCard(
                                    modifier = Modifier.clip(CircleShape),
                                    onClick = when (menu) {
                                        is ProfileMenu.LogOut -> {
                                            { viewModel.endSession() }
                                        }

                                        is ProfileMenu.Promotions -> {
                                            { }
                                        }

                                        is ProfileMenu.Statistics -> {
                                            { }
                                        }

                                        else -> null
                                    },
                                    shape = CircleShape,
                                    colors = CardDefaults.cardColors().copy(
                                        containerColor = MaterialTheme.colorScheme.surfaceBright
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(20.dp),
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(painter = painterResource(id = menu.icon), contentDescription = menu.name)
                                        CustomText(
                                            text = menu.name
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun handleMenuClick(menu: ProfileMenu, navigator: Navigator, viewModel: ProfileViewModel): (() -> Unit)? {
        return when (menu) {
            is ProfileMenu.LogOut -> {
                { viewModel.endSession() }
            }
            is ProfileMenu.Promotions -> {
                { }
            }
            is ProfileMenu.Statistics -> {
                { }
            }
            else -> null
        }
    }
}
