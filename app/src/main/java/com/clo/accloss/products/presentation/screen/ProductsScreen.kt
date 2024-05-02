package com.clo.accloss.products.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.products.presentation.components.ProductContent
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel

object ProductsScreen : Screen {
    private fun readResolve(): Any = ProductsScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<ProductViewModel>()
        val state by viewModel.state.collectAsState()

        state.products.DisplayResult(
            onLoading = { LoadingScreen() },
            onError = {
                ErrorScreen(it)
            },
            onSuccess = { list ->
                ProductContent(
                    products = list,
                    isRefreshing = state.reload ?: false,
                    onSelect = { codigo ->
                    },
                    onRefresh = {
                        viewModel.onRefresh()
                    }
                )
            }
        )
    }
}
