package com.clo.accloss.statistic.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.clo.accloss.core.presentation.components.CustomText
import com.clo.accloss.core.presentation.components.DefaultBackArrow
import com.clo.accloss.core.presentation.components.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.DefaultTopBar
import com.clo.accloss.core.presentation.components.ListFooter
import com.clo.accloss.statistic.presentation.components.PersonalCardComponent
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import com.clo.accloss.statistic.presentation.viewmodel.StatisticsViewModel

object StatisticsScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<StatisticsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()



        DefaultLayoutComponent(
            topBar = {
                DefaultTopBar(
                    title = {
                        CustomText(
                            text = stringResource(id = R.string.statistics),
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
            },
            state = state.salesmen
        ) { list ->
            StatisticsContent(
                personalStatistics = list,
                onSelect = { salesman ->
                    navigator.push(StatisticDetailsScreen(salesman))
                }
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun StatisticsContent(
        personalStatistics: List<PersonalStatistics>,
        onSelect: (String) -> Unit
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(5.dp)
        ) {
            personalStatistics.groupBy {
                it.statistic?.nomcoord
            }.forEach { (initial, salesman) ->
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        initial?.let {
                            CustomText(
                                text = it,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                            )
                        }
                    }
                }

                items(salesman) { statistics ->
                    statistics.statistic?.let {
                        PersonalCardComponent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 50.dp),
                            code = it.vendedor,
                            name = "${it.nombrevend} ${it.vendedor}",
                            onClick = onSelect
                        )
                    }
                }
            }

            item {
                ListFooter(text = stringResource(id = R.string.end_of_list))
            }
        }
    }
}
