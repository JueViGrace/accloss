package com.clo.accloss.salesman.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.salesman.domain.model.Salesman
import com.clo.accloss.salesman.presentation.components.SalesmanContent
import com.clo.accloss.salesman.presentation.viewmodel.SalesmanViewModel
import org.koin.core.parameter.parametersOf

data class SalesmanScreen(
    val id: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SalesmanViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        var title by rememberSaveable {
            mutableStateOf("")
        }

        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = {
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
                state.salesman.DisplayResult(
                    onLoading = {
                        LoadingScreen()
                    },
                    onError = {
                        ErrorScreen(it)
                    },
                    onSuccess = { salesman: Salesman ->
                        title = salesman.nombre
                        SalesmanContent(
                            salesman = salesman
                        )
                    },
                )
            }
        }
    }
}
