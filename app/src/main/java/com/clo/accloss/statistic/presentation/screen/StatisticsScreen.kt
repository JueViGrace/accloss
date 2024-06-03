package com.clo.accloss.statistic.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultBackArrow
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultLayoutComponent
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBar
import com.clo.accloss.core.presentation.components.LayoutComponents.DefaultTopBarActions
import com.clo.accloss.core.presentation.components.ListComponents.CustomLazyColumn
import com.clo.accloss.core.presentation.components.ListComponents.ListFooter
import com.clo.accloss.core.presentation.components.ListComponents.ListStickyHeader
import com.clo.accloss.core.presentation.components.TextFieldComponents.SearchBarComponent
import com.clo.accloss.core.presentation.components.TopBarActions
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import com.clo.accloss.statistic.presentation.viewmodel.StatisticsViewModel
import org.koin.core.parameter.parametersOf

data class StatisticsScreen(
    val id: String = ""
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<StatisticsViewModel>(parameters = { parametersOf(id) })
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
                                text = stringResource(id = R.string.statistics),
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
            state = state.salesmen
        ) { list ->
            if (id.isNotEmpty()) {
                ManagementsContent(
                    personalStatistics = list,
                    onClick = { code ->
                        navigator.push(StatisticDetailsScreen(code))
                    }
                )
            } else {
                StatisticsContent(
                    personalStatistics = list,
                    onSelect = { salesman ->
                        navigator.push(StatisticDetailsScreen(salesman))
                    }
                )
            }
        }
    }

    @Composable
    private fun ManagementsContent(
        modifier: Modifier = Modifier,
        personalStatistics: List<PersonalStatistics>,
        onClick: (String) -> Unit
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            items(personalStatistics) { personalStatistic ->
                PersonalCardComponent(
                    modifier = Modifier.defaultMinSize(minHeight = 130.dp, minWidth = 130.dp),
                    code = personalStatistic.codigo,
                    name = personalStatistic.nombre,
                    onClick = onClick
                )
            }
        }
    }

    @Composable
    private fun StatisticsContent(
        personalStatistics: List<PersonalStatistics>,
        onSelect: (String) -> Unit
    ) {
        CustomLazyColumn(
            items = personalStatistics,
            grouped = personalStatistics.groupBy { it.statistic?.nomcoord },
            stickyHeader = { char ->
                char?.let {
                    ListStickyHeader(
                        text = it
                    )
                }
            },
            content = { state ->
                AnimatedContent(
                    targetState = state,
                    label = stringResource(R.string.salesman)
                ) { personalStatistic ->
                    personalStatistic.statistic?.let { statistic ->
                        PersonalCardComponent(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            code = statistic.vendedor,
                            name = "${statistic.nombrevend} ${statistic.vendedor}",
                            onClick = onSelect
                        )
                    }
                }
            },
            footer = {
                ListFooter(text = stringResource(R.string.end_of_list))
            },
        )
    }

    @Composable
    private fun PersonalCardComponent(
        modifier: Modifier = Modifier,
        code: String,
        name: String,
        onClick: (String) -> Unit
    ) {
        CustomClickableCard(
            modifier = modifier.fillMaxSize(),
            onClick = { onClick(code) },
            shape = RoundedCornerShape(10),
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    text = name,
                    maxLines = 10,
                    softWrap = true,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
