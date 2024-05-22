package com.clo.accloss.products.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ErrorScreen
import com.clo.accloss.core.presentation.components.LoadingScreen
import com.clo.accloss.products.presentation.components.ProductDetailContent
import com.clo.accloss.products.presentation.viewmodel.ProductDetailViewModel
import org.koin.core.parameter.parametersOf

data class ProductDetailScreen(
    val id: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProductDetailViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        var title by rememberSaveable {
            mutableStateOf("")
        }

        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = {
                        CustomText(
                            text = title,
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
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                state.product.DisplayResult(
                    onLoading = {
                        LoadingScreen()
                    },
                    onError = {
                        ErrorScreen(it)
                    },
                    onSuccess = { product ->
                        title = product.codigo
                        ProductDetailContent(product = product)
                    },
                )
            }
        }
    }
}
