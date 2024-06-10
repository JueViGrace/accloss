package com.clo.accloss.statistic.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.clo.accloss.bills.presentation.screens.BillsScreen
import com.clo.accloss.core.common.roundFormat
import com.clo.accloss.core.presentation.components.DisplayComponents.CardLabel
import com.clo.accloss.core.presentation.components.DisplayComponents.CustomText
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBarActions
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.core.presentation.components.TopBarActions
import com.clo.accloss.customer.presentation.screens.CustomersScreen
import com.clo.accloss.order.presentation.screens.OrdersScreen
import com.clo.accloss.statistic.domain.model.Statistic
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import com.clo.accloss.statistic.presentation.viewmodel.StatisticDetailsViewModel
import org.koin.core.parameter.parametersOf

data class StatisticDetailsScreen(
    val id: String
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<StatisticDetailsViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsStateWithLifecycle()
        var title by remember {
            mutableStateOf("")
        }

        DefaultLayoutComponent(
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
                    },
                    actions = if (!id.startsWith("C")) {
                        {
                            DefaultTopBarActions(
                                onMenuClick = { action ->
                                    when {
                                        action is TopBarActions.Customers -> {
                                            navigator.push(CustomersScreen(id))
                                        }

                                        action is TopBarActions.Orders -> {
                                            navigator.push(OrdersScreen(id))
                                        }

                                        action is TopBarActions.Bills -> {
                                            navigator.push(BillsScreen(id))
                                        }
                                    }
                                },
                                items = listOf(
                                    TopBarActions.Customers,
                                    TopBarActions.Orders,
                                    TopBarActions.Bills
                                )
                            )
                        }
                    } else {
                        {}
                    }
                )
            },
            state = state.personalStatistics
        ) { personalStatistics ->
            title = personalStatistics.nombre
            PersonalStatisticsComponent(
                personalStatistics = personalStatistics
            )
        }
    }

    @Composable
    private fun PersonalStatisticsComponent(
        modifier: Modifier = Modifier,
        personalStatistics: PersonalStatistics
    ) {
        val amountsList = mapOf(
            Pair(R.string.debts, personalStatistics.deuda),
            Pair(R.string.expired, personalStatistics.vencido),
            Pair(R.string.net_billed_amount, personalStatistics.mtofactneto),
            Pair(R.string.charged, personalStatistics.mtocob),
            Pair(R.string.average_amount_per_document, personalStatistics.prommtopordoc),
            Pair(R.string.total_sold_amount, personalStatistics.totmtodocs)
        )

        val quantitiesList = mapOf(
            Pair(R.string.orders_amount, personalStatistics.cantped),
            Pair(R.string.document_amount, personalStatistics.cantdocs),
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .requiredSize(210.dp)
                        .clip(CircleShape)
                        .border(
                            color = MaterialTheme.colorScheme.primary,
                            width = 2.dp,
                            shape = CircleShape
                        )
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomText(
                            text = "${stringResource(id = R.string.goal)}: ${personalStatistics.meta.roundFormat()} $"
                        )
                        CustomText(
                            text = "${stringResource(
                                id = R.string.goal_percentage
                            )}: ${personalStatistics.prcmeta.roundFormat()} %"
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        amountsList.forEach { (key, value) ->
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = key,
                                value = "${value.roundFormat()} $"
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        quantitiesList.forEach { (key, value) ->
                            CardLabel(
                                modifier = Modifier.fillMaxWidth(),
                                title = key,
                                value = value.roundFormat(0)
                            )
                        }

                        CardLabel(
                            modifier = Modifier.fillMaxWidth(),
                            title = R.string.average_number_of_days_between_sales,
                            value = "${personalStatistics.promdiasvta.roundFormat(0)} ${
                                stringResource(
                                    R.string.days
                                )
                            }"
                        )
                    }
                }
            }

            if (personalStatistics.statistic != null) {
                item {
                    Row(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f))
                        CustomText(
                            modifier = Modifier.weight(3f),
                            text = stringResource(R.string.general_statistics),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            textAlign = TextAlign.Center,
                            softWrap = false
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f))
                    }
                }

                item {
                    StatisticBody(personalStatistics.statistic)
                }
            }

            item {
                ListFooter()
            }
        }
    }

    @Composable
    private fun StatisticBody(
        statistic: Statistic
    ) {
        val generalStats = mapOf(
            Pair(R.string.amount_in_orders, statistic.mtopedidos),
            Pair(R.string.amount_in_credit_notes_by_pp, statistic.ppgdolTotneto),
            Pair(R.string.amount_returned, statistic.devdolTotneto),
            Pair(R.string.bad_billing_return, statistic.defdolTotneto),
            Pair(R.string.amount_by_complaints, statistic.mtorecl),
        )

        val quantitiesStats = mapOf(
            Pair(R.string.customer_quantity, statistic.cntclientes),
            Pair(R.string.customers_visited, statistic.clivisit),
            Pair(R.string.complaints_quantity, statistic.cntrecl),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                generalStats.forEach { (key, value) ->
                    CardLabel(
                        modifier = Modifier.fillMaxWidth(),
                        title = key,
                        value = "${value.roundFormat()} $"
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                quantitiesStats.forEach { (key, value) ->
                    CardLabel(
                        modifier = Modifier.fillMaxWidth(),
                        title = key,
                        value = value.roundFormat(0)
                    )
                }

                CardLabel(
                    modifier = Modifier.fillMaxWidth(),
                    title = R.string.date_of_analysis,
                    value = statistic.fechaEstad
                )
            }
        }
    }
}
