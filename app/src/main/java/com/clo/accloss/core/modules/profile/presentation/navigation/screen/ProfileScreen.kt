package com.clo.accloss.core.modules.profile.presentation.navigation.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.common.Constants.profileMenu
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.modules.home.presentation.navigation.screen.HomeScreen
import com.clo.accloss.core.presentation.components.CustomClickableCard
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponent
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.components.MenuItem
import com.clo.accloss.core.modules.profile.presentation.components.ProfileMenu
import com.clo.accloss.core.modules.profile.presentation.viewmodel.ProfileViewModel
import com.clo.accloss.core.modules.syncronize.presentation.screen.SynchronizeScreen
import com.clo.accloss.login.presentation.components.AddAccountDialog
import com.clo.accloss.login.presentation.viewmodel.LoginViewModel
import com.clo.accloss.session.presentation.components.SessionsBody
import org.koin.core.parameter.parametersOf

class ProfileScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProfileViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val newSession = viewModel.newSession

        var showDialog by remember {
            mutableStateOf(false)
        }

        val loginViewModel = koinScreenModel<LoginViewModel>()
        val loginState by loginViewModel.state.collectAsStateWithLifecycle()

        newSession?.let { session ->
            if (showDialog) {
                AddAccountDialog(
                    showChanged = { newValue ->
                        showDialog = newValue
                    },
                    editCompany = loginViewModel.newCompany,
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
                                navigator.parent?.parent?.replaceAll(HomeScreen())
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
                        text = "${stringResource(R.string.management_finances)}: ${session.user}",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    )
                }

                state.profileStatistics.DisplayResult(
                    onLoading = { LoadingComponent() },
                    onError = {
                        ErrorComponent(it)
                    },
                    onSuccess = { statistics ->
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
                                    CustomText(text = stringResource(R.string.debts))
                                    CustomText(
                                        text = "$ ${statistics.debts.roundFormat()}",
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
                                    CustomText(text = stringResource(R.string.expired))
                                    CustomText(
                                        text = "$ ${statistics.expired.roundFormat()}",
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
                                    CustomText(text = stringResource(R.string.paid))
                                    CustomText(
                                        text = "$ ${statistics.paid.roundFormat()}",
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                                    )
                                }
                            }
                        }
                    },
                )

                CustomClickableCard(
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        columns = GridCells.Adaptive(150.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                    ) {
                        items(
                            items = profileMenu,
                            key = { item -> item.name }
                        ) { menu ->
                            MenuItem(
                                name = stringResource(menu.name),
                                icon = painterResource(id = menu.icon),
                                onClick = handleMenuClick(menu, navigator, viewModel)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleMenuClick(menu: ProfileMenu, navigator: Navigator, viewModel: ProfileViewModel): () -> Unit {
        return when (menu) {
            is ProfileMenu.LogOut -> {
                {
                    viewModel.endSession()
                    navigator.parent?.parent?.replaceAll(HomeScreen())
                }
            }
            is ProfileMenu.Synchronize -> {
                { navigator.parent?.parent?.push(SynchronizeScreen) }
            }
        }
    }
}
