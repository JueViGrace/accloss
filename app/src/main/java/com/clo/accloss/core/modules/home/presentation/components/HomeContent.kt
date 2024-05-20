package com.clo.accloss.core.modules.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clo.accloss.core.common.Constants.homeTabs
import com.clo.accloss.core.presentation.components.BottomNavigationItem

@Composable
fun HomeContent(
    currentScreen: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    homeTabs.forEach { tab ->
                        BottomNavigationItem(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .padding(horizontal = 5.dp),
                            tab = tab,
                        )
                    }
                },
                contentPadding = PaddingValues(
                    top = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding(),
                    bottom = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding(),
                ),
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
