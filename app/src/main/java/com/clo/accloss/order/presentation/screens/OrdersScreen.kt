package com.clo.accloss.order.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.core.common.toDate
import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponents.ErrorScreen
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBarActions
import com.clo.accloss.core.presentation.components.ListComponents.CustomLazyColumn
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.core.presentation.components.ListComponents.ListStickyHeader
import com.clo.accloss.core.presentation.components.TextFieldComponents.SearchBarComponent
import com.clo.accloss.core.presentation.components.TopBarActions
import com.clo.accloss.order.domain.model.Order
import com.clo.accloss.order.presentation.viewmodel.OrdersViewModel
import org.koin.core.parameter.parametersOf

data class OrdersScreen(
    val id: String = "",
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<OrdersViewModel>(parameters = { parametersOf(id) })
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
                                text = stringResource(id = R.string.orders),
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
            state = state.orders
        ) { list ->
            if (list.isNotEmpty()) {
                OrdersContent(
                    modifier = Modifier.fillMaxSize(),
                    orders = list
                )
            } else {
                ErrorScreen(
                    message = stringResource(id = R.string.empty_list)
                )
            }
        }
    }

    @Composable
    private fun OrdersContent(
        modifier: Modifier = Modifier,
        orders: List<Order>
    ) {
        val navigator = LocalNavigator.currentOrThrow
        CustomLazyColumn(
            modifier = modifier,
            grouped = orders.groupBy { it.ktiCodven },
            items = orders,
            stickyHeader = { salesman ->
                salesman?.let { value ->
                    ListStickyHeader(
                        text = "${stringResource(id = R.string.salesman)} $value"
                    )
                }
            },
            content = { order ->
                OrderListComponent(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    order = order,
                    onClick = { orderId ->
                        navigator.push(OrderDetailsScreen(orderId))
                    }
                )
            },
            footer = {
                ListFooter(text = stringResource(id = R.string.end_of_list))
            },
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }

    @Composable
    private fun OrderListComponent(
        modifier: Modifier = Modifier,
        order: Order,
        onClick: (String) -> Unit
    ) {
        CustomClickableCard(
            modifier = modifier,
            onClick = { onClick(order.ktiNdoc) },
            shape = RoundedCornerShape(10),
            colors = CardDefaults.elevatedCardColors()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = "Doc: ${order.ktiNdoc}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )

                    CustomText(
                        text = "Nº ${order.ktiNroped.ifEmpty {
                            stringResource(id = R.string.not_specified)
                        }}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = "${stringResource(R.string.customer)}: ${order.ktiCodcli}",
                    )

                    CustomText(
                        text = "${stringResource(R.string.issue_date)}: ${order.ktiFchdoc.toDate().toStringFormat(1)}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
