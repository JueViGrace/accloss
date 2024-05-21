package com.clo.accloss.management.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.management.presentation.components.ManagementDetailsContent
import com.clo.accloss.management.presentation.viewmodel.ManagementDetailsViewModel
import org.koin.core.parameter.parametersOf

data class ManagementDetailsScreen(
    val code: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey + code

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ManagementDetailsViewModel>(parameters = { parametersOf(code) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        var title: String by remember {
            mutableStateOf("")
        }

        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = {
                        CustomText(
                            text = title,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        DefaultBackArrow {
                            navigator.pop()
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                state.management.DisplayResult(
                    onLoading = {
                        LoadingScreen()
                    },
                    onError = {
                        ErrorScreen(it)
                    },
                    onSuccess = { management ->
                        title = management.nombre
                        ManagementDetailsContent(
                            management = management
                        )
                    }
                )
            }
        }
    }
}
