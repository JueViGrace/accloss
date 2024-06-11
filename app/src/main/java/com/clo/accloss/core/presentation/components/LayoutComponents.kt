package com.clo.accloss.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponents.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingComponents.LoadingScreen
import com.clo.accloss.core.state.RequestState

object LayoutComponents {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DefaultTopBar(
        modifier: Modifier = Modifier,
        title: @Composable () -> Unit = {},
        actions: @Composable (RowScope.() -> Unit) = {},
        navigationIcon: @Composable () -> Unit = {}
    ) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions
        )
    }

    @Composable
    fun DefaultTopBarActions(
        onMenuClick: (TopBarActions) -> Unit,
        items: List<TopBarActions>
    ) {
        var actionsVisible by remember {
            mutableStateOf(false)
        }

        IconButton(
            onClick = {
                actionsVisible = !actionsVisible
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_more_vert_24px),
                contentDescription = "More options"
            )
        }

        DropdownMenu(
            expanded = actionsVisible,
            onDismissRequest = { actionsVisible = false }
        ) {
            items.forEach { action ->
                DropdownMenuItem(
                    leadingIcon = if (action.icon != null) {
                        {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = action.icon),
                                contentDescription = "Search"
                            )
                        }
                    } else {
                        null
                    },
                    text = {
                        CustomText(
                            text = stringResource(id = action.title),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                        )
                    },
                    onClick = {
                        onMenuClick(action)
                        actionsVisible = false
                    }
                )
            }
        }
    }

    @Composable
    fun<T> DefaultLayoutComponent(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit = {},
        state: RequestState<T>,
        content: @Composable (T) -> Unit
    ) {
        Scaffold(
            topBar = topBar
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                state.DisplayResult(
                    onLoading = {
                        LoadingScreen()
                    },
                    onError = { message ->
                        ErrorScreen(message)
                    },
                    onSuccess = { data ->
                        content(data)
                    },
                )
            }
        }
    }

    @Composable
    fun DefaultLayoutComponent(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        content: @Composable () -> Unit
    ) {
        Scaffold(
            topBar = topBar,
            floatingActionButton = floatingActionButton,
            bottomBar = bottomBar
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                content()
            }
        }
    }

    @Composable
    fun DefaultBackArrow(
        onClick: () -> Unit
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back_24px),
                contentDescription = "On back"
            )
        }
    }

    @Composable
    fun BottomNavigationItem(
        modifier: Modifier = Modifier,
        tab: Tab,
    ) {
        val tabNavigator = LocalTabNavigator.current
        val selected = tabNavigator.current.key == tab.key

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    tabNavigator.current = tab
                },
            ) {
                tab.options.icon?.let { icon ->
                    Icon(
                        modifier = Modifier.size(35.dp),
                        painter = icon,
                        contentDescription = tab.options.title,
                        tint = if (selected) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        }
                    )
                }
            }
            if (selected) {
                CustomText(
                    text = tab.options.title,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
