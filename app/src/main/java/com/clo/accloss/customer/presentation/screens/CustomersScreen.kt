package com.clo.accloss.customer.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.clo.accloss.R
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
import com.clo.accloss.customer.presentation.model.CustomerData
import com.clo.accloss.customer.presentation.viewmodel.CustomersViewModel

object CustomersScreen : Screen {
    override val key: ScreenKey = super.key + uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<CustomersViewModel>()
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
                                text = stringResource(id = R.string.customers),
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
            state = state.customers
        ) { list ->
            if (list.isNotEmpty()) {
                CustomersContent(
                    modifier = Modifier.fillMaxSize(),
                    customers = list
                )
            } else {
                ErrorScreen(message = stringResource(R.string.empty_list))
            }
        }
    }

    @Composable
    private fun CustomersContent(
        modifier: Modifier = Modifier,
        customers: List<CustomerData>
    ) {
        CustomLazyColumn(
            modifier = modifier,
            grouped = customers.groupBy { it.customer.nombre.first().toString() },
            items = customers,
            stickyHeader = { char ->
                char?.let { letter ->
                    ListStickyHeader(
                        text = letter
                    )
                }
            },
            content = { customer ->
                CustomerListComponent(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    customerData = customer,
                    onClick = { code ->
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
    private fun CustomerListComponent(
        modifier: Modifier = Modifier,
        customerData: CustomerData,
        onClick: (String) -> Unit
    ) {
        CustomClickableCard(
            modifier = modifier,
            onClick = { onClick(customerData.customer.codigo) },
            shape = RoundedCornerShape(10),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    text = customerData.customer.nombre,
                    maxLines = 10,
                    softWrap = true,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
