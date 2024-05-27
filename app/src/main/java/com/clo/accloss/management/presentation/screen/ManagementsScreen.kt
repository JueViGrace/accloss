package com.clo.accloss.management.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.clo.accloss.management.presentation.viewmodel.ManagementViewModel
import com.clo.accloss.statistic.presentation.components.PersonalCardComponent
import com.clo.accloss.statistic.presentation.model.PersonalStatistics
import com.clo.accloss.statistic.presentation.screen.StatisticDetailsScreen

object ManagementsScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ManagementViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        DefaultLayoutComponent(
            topBar = {
                DefaultTopBar(
                    title = {
                        CustomText(
                            text = stringResource(id = R.string.managements),
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
            state = state.managements
        ) { list ->
            ManagementsContent(
                personalStatistics = list,
                onClick = { code ->
                    navigator.push(StatisticDetailsScreen(code))
                }
            )
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
}
