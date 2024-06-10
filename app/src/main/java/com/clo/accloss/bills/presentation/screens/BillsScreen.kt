package com.clo.accloss.bills.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.clo.accloss.R
import com.clo.accloss.bills.domain.model.Bill
import com.clo.accloss.bills.presentation.viewmodel.BillsViewModel
import com.clo.accloss.core.common.Constants.calculateDocStatus
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.common.toDateFormat
import com.clo.accloss.core.common.toStringFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomClickableCard
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.ErrorComponents.ErrorScreen
import com.clo.accloss.core.presentation.components.LayoutComponents
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.ListComponents
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.core.presentation.components.ListComponents.ListStickyHeader
import com.clo.accloss.core.presentation.components.TextFieldComponents
import com.clo.accloss.core.presentation.components.TopBarActions
import org.koin.core.parameter.parametersOf
import java.util.Date

data class BillsScreen(
    val id: String = ""
) : Screen {
    override val key: ScreenKey = super.key + uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<BillsViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()

        val focus = LocalFocusManager.current

        DefaultLayoutComponent(
            topBar = {
                AnimatedVisibility(
                    visible = !state.searchBarVisibility,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    DefaultTopBar(
                        title = {
                            CustomText(
                                text = stringResource(id = R.string.bills),
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
                            LayoutComponents.DefaultTopBarActions(
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
                    visible = state.searchBarVisibility,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    DefaultTopBar(
                        title = {
                            TextFieldComponents.SearchBarComponent(
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
            state = state.bills
        ) { list ->
            if (list.isNotEmpty()) {
                BillsContent(
                    modifier = Modifier.fillMaxSize(),
                    bills = list
                )
            } else {
                ErrorScreen(
                    message = stringResource(id = R.string.empty_list)
                )
            }
        }
    }

    @Composable
    private fun BillsContent(
        modifier: Modifier = Modifier,
        bills: List<Bill>
    ) {
        val navigator = LocalNavigator.currentOrThrow
        ListComponents.CustomLazyColumn(
            modifier = modifier,
            grouped = bills.groupBy { it.vendedor },
            items = bills,
            stickyHeader = { salesman ->
                salesman?.let { value ->
                    ListStickyHeader(
                        text = "${stringResource(id = R.string.salesman)} $value"
                    )
                }
            },
            content = { bill ->
                BillListComponent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                    bill = bill,
                    onClick = { billId ->
                        navigator.push(BillDetailScreen(billId))
                    }
                )
            },
            footer = {
                ListFooter(text = stringResource(id = R.string.end_of_list))
            },
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }

    @Composable
    private fun BillListComponent(
        modifier: Modifier = Modifier,
        bill: Bill,
        onClick: (String) -> Unit
    ) {
        val debt = bill.dtotalfinal - bill.dtotpagos

        CustomClickableCard(
            modifier = modifier,
            onClick = {
                onClick(bill.documento)
            }
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
                    CustomText(text = "Nº ${bill.documento}")
                    CustomText(
                        text = stringResource(id = calculateDocStatus(bill.estatusdoc))
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(text = "${stringResource(R.string.amount)}: ${bill.dtotalfinal.roundFormat()} $")
                    CustomText(
                        text = "${stringResource(R.string.debt)}: ${debt.roundFormat()} $"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(text = "${stringResource(R.string.customer)}: ${bill.codcliente}")
                    CustomText(
                        text = calculateExpiringStatus(bill.vence),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )
                }
            }
        }
    }

    @Composable
    private fun calculateExpiringStatus(expires: String): String {
        val date1 = expires.toDateFormat(1)

        val date2 = Date().toStringFormat(1).toDateFormat(1)

        val diff = date1.time - date2.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            date1 == date2 -> {
                stringResource(R.string.expires_today)
            }

            date1 > date2 -> {
                "${
                    stringResource(id = R.string.expires)
                } ${
                    stringResource(id = R.string.in_string)
                } $days ${stringResource(id = R.string.days)}"
            }

            else -> {
                stringResource(R.string.expired)
            }
        }
    }
}
