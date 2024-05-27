package com.clo.accloss.products.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import com.clo.accloss.core.presentation.components.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.SearchBarComponent
import com.clo.accloss.products.presentation.components.ProductsContent
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel

object ProductsScreen : Screen {
    private fun readResolve(): Any = ProductsScreen

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProductViewModel>()
        val searchText by viewModel.searchText.collectAsStateWithLifecycle()
        val state by viewModel.state.collectAsStateWithLifecycle()

        var searchBarVisible by rememberSaveable {
            mutableStateOf(false)
        }

        val focus = LocalFocusManager.current

        DefaultLayoutComponent(
            topBar = {
                AnimatedVisibility(
                    visible = !state.searchBarVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
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
                        },
                        actions = {
                            IconButton(onClick = { viewModel.toggleVisibility(true) }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search_24px),
                                    contentDescription = "Search"
                                )
                            }
                        }
                    )
                }

                AnimatedVisibility(
                    visible = state.searchBarVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    DefaultTopBar(
                        title = {
                            SearchBarComponent(
                                query = searchText,
                                onQueryChange = viewModel::onSearchTextChange,
                                onSearch = {
                                    focus.clearFocus()
                                    viewModel.onSearchTextChange(it)
                                }
                            )
                        },
                        navigationIcon = {
                            DefaultBackArrow {
                                viewModel.onSearchTextChange("")
                                viewModel.toggleVisibility(false)
                            }
                        },
                    )
                }
            },
            state = state.products
        ) { products ->
            ProductsContent(
                products = products,
                isRefreshing = state.reload,
                onSelect = { code ->
                    navigator.push(ProductDetailScreen(code))
                },
                onRefresh = {
                    viewModel.onRefresh()
                }
            )
        }
    }
}
