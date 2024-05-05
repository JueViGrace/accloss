package com.clo.accloss.core.presentation.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.home.presentation.navigation.routes.HomeRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    homeRoute: Screen,
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .requiredWidth(300.dp)
            ) {
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
