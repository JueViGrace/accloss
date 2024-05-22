package com.clo.accloss.products.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.products.presentation.components.ProductsContent
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel

object ProductsScreen : Screen {
    private fun readResolve(): Any = ProductsScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProductViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = {
                        CustomText(
                            text = stringResource(id = R.string.products),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
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
                    .padding(innerPadding)
            ) {
                state.products.DisplayResult(
                    onLoading = { LoadingScreen() },
                    onError = {
                        ErrorScreen(it)
                    },
                    onSuccess = { list ->
                        ProductsContent(
                            products = list,
                            isRefreshing = state.reload,
                            onSelect = { code ->
                                navigator.push(ProductDetailScreen(code))
                            },
                            onRefresh = {
                                viewModel.onRefresh()
                            }
                        )
                    }
                )
            }
        }
    }
}
