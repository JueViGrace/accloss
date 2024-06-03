package com.clo.accloss.products.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clo.accloss.R
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBarActions
import com.clo.accloss.core.presentation.components.TopBarActions
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.core.presentation.components.ListComponents.PullToRefreshLazyColumn
import com.clo.accloss.core.presentation.components.TextFieldComponents.SearchBarComponent
import com.clo.accloss.products.domain.model.Product
import com.clo.accloss.products.presentation.viewmodel.ProductViewModel

object ProductsScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProductViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

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
                            DefaultTopBarActions(
                                onMenuClick = { action ->
                                    when {
                                        action is TopBarActions.Search -> {
                                            viewModel.toggleVisibility(true)
                                        }
                                    }
                                },
                                items = listOf(TopBarActions.Search)
                            )
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
                                query = state.searchText,
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

    @Composable
    private fun ProductsContent(
        modifier: Modifier = Modifier,
        products: List<Product>,
        isRefreshing: Boolean,
        onSelect: (String) -> Unit,
        onRefresh: () -> Unit
    ) {
        PullToRefreshLazyColumn(
            modifier = modifier,
            items = products,
            contentPadding = PaddingValues(5.dp),
            content = { product ->
                AnimatedContent(
                    targetState = product,
                    label = "Product list",
                    transitionSpec = {
                        slideInVertically(
                            initialOffsetY = { height -> height / 4 }
                        ) + fadeIn() togetherWith
                            slideOutVertically(
                                targetOffsetY = { height -> -height / 4 }
                            ) + fadeOut()
                    }
                ) {
                    ProductListContent(
                        product = it,
                        onSelect = onSelect
                    )
                }
            },
            footer = {
                ListFooter(text = stringResource(R.string.end_of_list))
            },
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        )
    }

    @Composable
    private fun ProductListContent(
        modifier: Modifier = Modifier,
        product: Product,
        onSelect: (String) -> Unit
    ) {
        val discount by rememberSaveable {
            mutableStateOf(product.dctotope > 0.0)
        }

        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CustomClickableCard(
                onClick = {
                    onSelect(product.codigo)
                },
                shape = RoundedCornerShape(10),
                border = when {
                    discount -> {
                        BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                    }
                    product.existencia == 0.0 -> {
                        BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.error)
                    }
                    else -> null
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .weight(0.5f)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                            .padding(10.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.url)
                            .crossfade(enable = true)
                            .crossfade(200)
                            .build(),
                        contentDescription = product.nombre
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.Start
                    ) {
                        CustomText(
                            text = product.nombre,
                            softWrap = true,
                            maxLines = 5,
                            textAlign = TextAlign.Justify
                        )

                        CustomText(
                            text = "${stringResource(id = R.string.code)}: ${product.codigo}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        )

                        CustomText(
                            text = "${stringResource(id = R.string.reference)}: ${product.referencia}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        )

                        if (product.existencia <= 0) {
                            CustomText(
                                text = stringResource(R.string.no_stock_available),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomText(
                                    text = "${product.precio1.roundFormat()} $",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    color = if (discount) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )

                                if (discount) {
                                    CustomText(
                                        text = stringResource(R.string.tap_to_see_discount),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
