package com.clo.accloss.core.presentation.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.LoadingComponent
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeRoutes
import com.clo.accloss.core.presentation.state.RequestState
import com.clo.accloss.session.domain.model.Session
import com.clo.accloss.session.presentation.components.SessionBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    homeRoute: Screen,
    currentSession: Session,
    sessions: RequestState<List<Session>>,
    onChangeSession: (Session) -> Unit,
    onAddSession: () -> Unit,
    currentScreen: @Composable () -> Unit,
    onMenuClick: (HomeRoutes) -> Unit,
    onEndSession: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val homeRoutes = HomeRoutes::class.sealedSubclasses
        .map { routes ->
            routes.objectInstance
        }

    var showAccounts by remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .requiredWidth(300.dp)
            ) {
                SessionBox(
                    session = currentSession,
                    onShowSessions = { newValue ->
                        showAccounts = newValue
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 5.dp)
                )

                if (showAccounts) {
                    sessions.DisplayResult(
                        onLoading = {
                            LoadingComponent()
                        },
                        onError = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomText(
                                    text = "Algo salió mal",
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                                )
                            }
                        },
                        onSuccess = { list ->
                            Column {
                                list.forEach { session ->

                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                onChangeSession(session)
                                            }
                                            .padding(vertical = 20.dp, horizontal = 15.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(
                                                5.dp,
                                                Alignment.Start
                                            )
                                        ) {
                                            if (session.active) {
                                                Icon(
                                                    modifier = Modifier.size(15.dp),
                                                    imageVector = Icons.Filled.CheckCircle,
                                                    contentDescription = "Active icon"
                                                )
                                            }

                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_account_circle_24px),
                                                contentDescription = "Account icon"
                                            )
                                            CustomText(
                                                text = "${session.nombre}, ${session.nombreEmpresa}",
                                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                            )
                                        }
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .clickable {
                                            onAddSession()
                                        }
                                        .padding(vertical = 20.dp, horizontal = 15.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            5.dp,
                                            Alignment.Start
                                        )
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_add_circle_24px),
                                            contentDescription = "Account"
                                        )
                                        CustomText(
                                            text = "Agregar otra cuenta",
                                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )
                                    }
                                }
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }

                homeRoutes.forEach { routes ->
                    routes?.let { item ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            onClick = {
                                onMenuClick(item)
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            label = {
                                CustomText(
                                    text = item.title,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            selected = homeRoute == item.screen,
                            icon = if (item.icon != null) {
                                {
                                    Icon(
                                        painter = painterResource(item.icon),
                                        contentDescription = item.title
                                    )
                                }
                            } else {
                                null
                            }
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 5.dp)
                )

                NavigationDrawerItem(
                    onClick = {
                        onEndSession()
                    },
                    label = {
                        CustomText(
                            text = "Cerrar Sesión",
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    selected = false
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        homeRoutes.first { it?.screen == homeRoute }?.title?.let { title ->
                            CustomText(
                                text = title,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                currentScreen()
            }
        }
    }
}
